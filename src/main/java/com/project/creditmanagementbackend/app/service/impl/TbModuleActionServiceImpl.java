package com.project.creditmanagementbackend.app.service.impl;

import com.project.creditmanagementbackend.app.dao.TbActionDao;
import com.project.creditmanagementbackend.app.dao.TbModuleActionDao;
import com.project.creditmanagementbackend.app.dao.TbModuleDao;
import com.project.creditmanagementbackend.app.exception.BusinessException;
import com.project.creditmanagementbackend.app.exception.module.ActionErrors;
import com.project.creditmanagementbackend.app.exception.module.ModuleErrors;
import com.project.creditmanagementbackend.app.service.TbModuleActionService;
import com.project.creditmanagementbackend.domain.dto.request.PermisoRequestDto;
import com.project.creditmanagementbackend.domain.entity.TbAction;
import com.project.creditmanagementbackend.domain.entity.TbModule;
import com.project.creditmanagementbackend.domain.entity.TbModuleAction;
import com.project.creditmanagementbackend.domain.mapper.TbModuleActionMapper;
import com.project.creditmanagementbackend.domain.mapper.TbModuleMapper;
import com.project.creditmanagementbackend.domain.dto.TbModuleDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class TbModuleActionServiceImpl implements TbModuleActionService {

    private final TbModuleActionDao moduloAccionDao;
    private final TbModuleDao moduloDao;
    private final TbActionDao accionDao;
    private final TbModuleActionMapper moduloAccionMapper;
    private final TbModuleMapper moduloMapper;

    @Override
    @Transactional
    public List<TbModuleDto> updateModuleActions(Integer moduleId, List<Integer> actionIds) {
        PermisoRequestDto request = new PermisoRequestDto();
        request.setModuloId(moduleId);
        request.setAccionId(actionIds);
        return updateModuleActions(List.of(request));
    }

    @Transactional
    public List<TbModuleDto> updateModuleActions(List<PermisoRequestDto> requests) {
        if (requests == null || requests.isEmpty()) {
            return List.of();
        }
        record Key(Integer moduleId, Integer actionId) {}
        List<Integer> moduleIds = requests.stream()
                .map(PermisoRequestDto::getModuloId)
                .distinct()
                .toList();

        List<TbModule> modules = moduloDao.findAllById(moduleIds);

        if (modules.size() != moduleIds.size()) {
            throw new BusinessException(ModuleErrors.MODULO_NOT_FOUND);
        }

        Set<Key> requestedKeys = requests.stream()
                .filter(r -> r.getAccionId() != null)
                .flatMap(r -> r.getAccionId()
                        .stream()
                        .map(actionId -> new Key(r.getModuloId(), actionId)))
                .collect(Collectors.toSet());

        List<Integer> allActionIds = requestedKeys.stream()
                .map(Key::actionId)
                .distinct()
                .toList();

        List<TbAction> actions = accionDao.findAllById(allActionIds);

        if (!allActionIds.isEmpty() && actions.size() != allActionIds.size()) {
            throw new BusinessException(ActionErrors.ACCION_NOT_FOUND);
        }

        List<TbModuleAction> existingAssociations =
                moduloAccionDao.findAllByModuloIdInIncludeDeleted(moduleIds);

        Map<Key, TbModuleAction> existingMap = existingAssociations.stream()
                .collect(Collectors.toMap(
                        a -> new Key(a.getId().getModuloId(), a.getId().getAccionId()),
                        a -> a
                ));

        Map<Integer, TbModule> moduleMap = modules.stream()
                .collect(Collectors.toMap(TbModule::getId, m -> m));

        Map<Integer, TbAction> actionMap = actions.stream()
                .collect(Collectors.toMap(TbAction::getId, a -> a));

        LocalDateTime now = LocalDateTime.now();

        List<TbModuleAction> newAssociations = requestedKeys.stream()
                .filter(key -> !existingMap.containsKey(key))
                .map(key -> {
                    TbModule module = moduleMap.get(key.moduleId());
                    TbAction action = actionMap.get(key.actionId());

                    TbModuleAction newAssoc =
                            moduloAccionMapper.toEntity(module, action, now);

                    if (module.getActions() != null) {
                        module.getActions().add(newAssoc);
                    }

                    return newAssoc;
                })
                .toList();

        existingAssociations.forEach(a -> {
            Key key = new Key(a.getId().getModuloId(), a.getId().getAccionId());
            a.setEstado(requestedKeys.contains(key));
            a.setActualizadoEn(now);
            a.setEliminadoEn(null);
        });

        moduloAccionDao.saveAll(newAssociations);
        moduloAccionDao.saveAll(existingAssociations);

        List<TbModuleAction> updatedAssociations =
                moduloAccionDao.findAllByModuloIdInIncludeDeleted(moduleIds);

        modules.forEach(module -> {

            List<TbModuleAction> activeActions = updatedAssociations.stream()
                    .filter(a -> a.getId().getModuloId().equals(module.getId()))
                    .filter(TbModuleAction::isEstado)
                    .toList();

            module.setActions(activeActions);
        });

        return modules.stream()
                .map(moduloMapper::toDto)
                .toList();
    }
}
