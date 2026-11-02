package com.project.creditmanagementbackend.app.service.seeder.impl;

import com.project.creditmanagementbackend.app.dao.*;
import com.project.creditmanagementbackend.app.service.seeder.DataSeeder;
import com.project.creditmanagementbackend.domain.entity.*;
import com.project.creditmanagementbackend.domain.enumerado.ModuleType;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Component
@RequiredArgsConstructor
public class SystemSeeder implements DataSeeder {

    private final TbModuleDao moduloDao;
    private final TbModuleActionDao moduleActionDao;
    private final TbActionDao accionDao;
    private final TbRolePermissionDao rolPermisoDao;
    private final TbRoleDao rolDao;
    private final TbUserDao usuarioDao;
    private final PasswordEncoder passwordEncoder;

    @Override
    public String getName() {
        return "system";
    }

    @Override
    public int getOrder() {
        return 1;
    }

    @Override
    public void seed() {
        List<TbModule> allModules = new ArrayList<>();

        TbModule dashboard = createGrupoIfNotFound("DASHBOARD", "Dashboard", "/dashboard", "fa-tachometer-alt", 1);
        TbModule personas = createGrupoIfNotFound("ACT_FINAN", "Actores Financieros", null, "fa-users-cog", 2);
        TbModule creditos = createGrupoIfNotFound("OPERACIONES_CREDITO", "Operaciones de Crédito", null, "fa-file-invoice-dollar", 3);
        TbModule cobranza = createGrupoIfNotFound("COBRANZA", "Cobranza", "/cobranza", null, 4);
        TbModule inversiones = createGrupoIfNotFound("INVERSIONES", "Inversiones", null, "fa-piggy-bank", 5);
        TbModule finanzas = createGrupoIfNotFound("FINANZAS", "Finanzas", null, "fa-wallet", 6);
        TbModule recuperacion = createGrupoIfNotFound("RECUPERACION", "Recuperación", null, "fa-gavel", 7);
        TbModule cierre = createGrupoIfNotFound("CIERRE_OPERACIONES", "Cierre Operaciones", null, "fa-check-circle", 8);
        TbModule reportes = createGrupoIfNotFound("REPORTES", "Reportes", null, "fa-chart-pie", 9);
        TbModule admin = createGrupoIfNotFound("ADMINISTRACION", "Administración", null, "fa-cogs", 10);

        allModules.add(createModuloIfNotFound("CLIENTES","Prestatarios","/personas/clientes","fa-users",1,personas, true));
        allModules.add(createModuloIfNotFound("INVERSIONISTAS","Inversionistas","/personas/inversionistas","fa-hand-holding-usd",2,personas, true));

        allModules.add(createModuloIfNotFound("CREDITOS","Créditos","/creditos","fa-credit-card",1,creditos, true));
        allModules.add(createModuloIfNotFound("GARANTIAS","Garantías","/creditos/garantias","fa-shield-alt",2,creditos, true));
        allModules.add(createModuloIfNotFound("CRONOGRAMA_PAGOS","Cronograma de Pagos","/creditos/cronograma","fa-calendar-alt",3,creditos, true));
        allModules.add(createModuloIfNotFound("DESEMBOLSOS","Desembolsos","/creditos/desembolsos","fa-university",4,creditos, true));

        allModules.add(createModuloIfNotFound("REGISTRO_PAGOS","Registro de Pagos","/cobranza/pagos","fa-cash-register",1,cobranza, true));
        allModules.add(createModuloIfNotFound("PENALIDADES","Penalidades","/cobranza/penalidades","fa-exclamation-circle",2,cobranza, true));

        allModules.add(createModuloIfNotFound("APORTES_INVERSIONISTAS","Aportes Inversionistas","/inversiones/aportes","fa-wallet",1,inversiones, true));
        allModules.add(createModuloIfNotFound("PAGOS_INVERSIONISTAS","Pagos Inversionistas","/inversiones/pagos","fa-coins",2,inversiones, true));
        allModules.add(createModuloIfNotFound("HISTORIAL_INVERSIONES","Historial Inversiones","/inversiones/historial","fa-history",3,inversiones, true));

        allModules.add(createModuloIfNotFound("GASTOS_OPERATIVOS","Gastos Operativos","/finanzas/gastos","fa-receipt",1,finanzas, true));
        allModules.add(createModuloIfNotFound("RENTABILIDAD_CREDITO","Rentabilidad Crédito","/finanzas/rentabilidad","fa-chart-pie",2,finanzas, true));
        allModules.add(createModuloIfNotFound("FLUJO_FINANCIERO","Flujo Financiero","/finanzas/flujo","fa-chart-area",3,finanzas, true));

        allModules.add(createModuloIfNotFound("EJECUCION_GARANTIA","Ejecución Garantía","/recuperacion/garantia","fa-gavel",1,recuperacion, true));

        allModules.add(createModuloIfNotFound("LIQUIDACION_CREDITO","Liquidación Crédito","/cierre/liquidacion","fa-check-circle",1,cierre, true));
        allModules.add(createModuloIfNotFound("LIBERACION_GARANTIA","Liberación Garantía","/cierre/liberacion","fa-lock-open",2,cierre, true));
        allModules.add(createModuloIfNotFound("HISTORIAL_CREDITOS","Historial Créditos","/cierre/historial","fa-archive",3,cierre, true));

        allModules.add(createModuloIfNotFound("CREDITOS_PERIODO","Créditos Periodo","/reportes/creditos-periodo","fa-calendar-check",4,reportes, true));
        allModules.add(createModuloIfNotFound("INVERSIONES_ACTIVAS","Inversiones Activas","/reportes/inversiones-activas","fa-layer-group",5,reportes, true));

        TbModule modUsuarios = createModuloIfNotFound("USUARIOS","Usuarios","/administracion/usuarios","fa-user",1,admin, true);
        TbModule modRoles = createModuloIfNotFound("ROLES","Roles","/administracion/roles","fa-user-shield",2,admin, true);
        TbModule modModulos = createModuloIfNotFound("MODULOS","Módulos","/administracion/modulos","fa-clipboard-list",3,admin, true);
        TbModule modAcciones = createModuloIfNotFound("ACCION","Acciones","/administracion/acciones","fa-clipboard-list",4,admin, true);

        allModules.add(modUsuarios);
        allModules.add(modRoles);
        allModules.add(modModulos);
        allModules.add(modAcciones);

        TbAction accListar   = createAccionIfNotFound("LISTAR", "Listar", "fa-solid fa-list", "#4CAF50");
        TbAction accAgregar  = createAccionIfNotFound("AGREGAR", "Agregar", "fa-solid fa-plus-circle", "#2196F3");
        TbAction accEditar   = createAccionIfNotFound("EDITAR", "Editar","fa-solid fa-edit", "#FFC107");
        TbAction accEliminar = createAccionIfNotFound("ELIMINAR", "Eliminar","fa-solid fa-trash-alt", "#F44336");
        TbAction accAsignarPermiso= createAccionIfNotFound("ACC-ASIGNAR-PERMISOS", "Asignar permisos", "fa-solid fa-user-shield", "#499E6D");
        TbAction accAsignarAcciones= createAccionIfNotFound("ACC-ASIGNAR-ACCIONES", "Asignar acciones","fa-solid fa-user-shield","#499E6D");
        TbAction accCargarDocumentos = createAccionIfNotFound("CARGAR_DOCUMENTOS", "", "fa-solid fa-file-upload", "#607D8B");

        /* ----------------- CREAR ROL ADMIN -------------------*/
        TbRole rolAdmin = rolDao.findByName("Administrador").orElseGet(() -> {
            TbRole role = new TbRole();
            role.setName("Administrador");
            role.setCode("ADMIN");
            role.setState(true);
            role.setCreatedAt(LocalDateTime.now());
            return rolDao.save(role);
        });

        /* ----------------- ASIGNAR ACCIONES A MODULOS -------------------*/
        TbModule modClientes = moduloDao.findByCode("CLIENTES").get();

        List<TbAction> accionesBasicas = List.of(accListar, accAgregar, accEditar, accEliminar);

        List<TbAction> accionesClientes = List.of(
                accListar, accAgregar, accEditar, accEliminar, accCargarDocumentos
        );

        List<TbAction> accionesModulo = List.of(
                accListar, accAgregar, accEditar, accEliminar, accAsignarAcciones
        );

        List<TbAction> accionesRoles = List.of(
                accListar, accAgregar, accEditar, accEliminar, accAsignarPermiso
        );

        assignActionsToModule(modClientes, accionesClientes);
        assignActionsToModule(modRoles, accionesRoles);
        assignActionsToModule(modModulos, accionesModulo);
        assignActionsToModule(modUsuarios, accionesBasicas);
        assignActionsToModule(modAcciones, accionesBasicas);

        /* ----------------- ASIGNAR PERMISOS A ADMINISTRADOR -------------------*/

        accionesBasicas.forEach(acc -> {
            createPermisoIfNotFound(rolAdmin, modAcciones, acc, true);
            createPermisoIfNotFound(rolAdmin, modUsuarios, acc, true);
        });

        accionesClientes.forEach(acc ->
                createPermisoIfNotFound(rolAdmin, modClientes, acc, true)
        );

        accionesModulo.forEach(acc ->
                createPermisoIfNotFound(rolAdmin, modModulos, acc, true)
        );

        accionesRoles.forEach(acc ->
                createPermisoIfNotFound(rolAdmin, modRoles, acc, true)
        );

        /*----------- CREAR USUARIO ADMIN --------*/
        usuarioDao.findByUsername("administrador").orElseGet(() -> {
            TbUser u = new TbUser();
            u.setUsername("administrador");
            u.setNames("Administrador Sistema");
            u.setState(true);
            u.setRole(rolAdmin);
            u.setEmail("administrador@gmail.com");
            u.setPassword(passwordEncoder.encode("admin1234"));
            u.setCreatedAt(LocalDateTime.now());
            return usuarioDao.save(u);
        });
    }

    /*----------- METODOS DE CREACION --------*/
    private TbModule createModuloIfNotFound(String code, String name, String route, String icon, Integer order, TbModule parent, Boolean state) {
        return moduloDao.findByCode(code)
                .orElseGet(() -> {
                    TbModule m = new TbModule();
                    m.setCode(code);
                    m.setName(name);
                    m.setRoute(route);
                    m.setIcon(icon);
                    m.setType(ModuleType.MODULO);
                    m.setOrderNum(order);
                    m.setParent(parent);
                    m.setState(state);
                    m.setCreatedAt(LocalDateTime.now());
                    return moduloDao.save(m);
                });
    }

    private TbAction createAccionIfNotFound(String code, String name, String icon, String color) {
        return accionDao.findByCode(code)
                .orElseGet(() -> {
                    TbAction a = new TbAction();
                    a.setCode(code);
                    a.setName(name);
                    a.setIcon(icon);
                    a.setColor(color);
                    a.setState(true);
                    a.setCreatedAt(LocalDateTime.now());
                    return accionDao.save(a);
                });
    }
    private TbModule createGrupoIfNotFound(String code, String name, String route, String icon, Integer order) {
        return moduloDao.findByCode(code)
                .orElseGet(() -> {
                    TbModule m = new TbModule();
                    m.setCode(code);
                    m.setName(name);
                    m.setRoute(route);
                    m.setIcon(icon);
                    m.setType(ModuleType.GRUPO);
                    m.setState(true);
                    m.setOrderNum(order);
                    m.setCreatedAt(LocalDateTime.now());
                    return moduloDao.save(m);
                });
    }

    private void createPermisoIfNotFound(TbRole role, TbModule module, TbAction action, Boolean state) {
        TbRolePermissionId id = new TbRolePermissionId(role.getId(), module.getId(), action.getId());
        if (!rolPermisoDao.existsById(id)) {
            TbRolePermission p = new TbRolePermission();
            p.setId(id);
            p.setRole(role);
            p.setModule(module);
            p.setAction(action);
            p.setState(state);
            p.setCreatedAt(LocalDateTime.now());
            rolPermisoDao.save(p);
        }
    }

    private void assignActionsToModule(TbModule module, List<TbAction> actions) {
        actions.forEach(action -> {
            if (!moduleActionDao.existsByIdModuloIdAndIdAccionId(module.getId(), action.getId())) {
                TbModuleAction ma = new TbModuleAction();
                ma.setModulo(module);
                ma.setAccion(action);
                ma.setCreadoEn(LocalDateTime.now());
                moduleActionDao.save(ma);
            }
        });
    }
}
