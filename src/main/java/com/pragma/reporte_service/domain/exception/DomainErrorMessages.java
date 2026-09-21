package com.pragma.reporte_service.domain.exception;

public final class DomainErrorMessages {

    public static final String BOOTCAMP_ID_REQUIRED = "El campo bootcampId es obligatorio";
    public static final String NAME_REQUIRED = "El campo name es obligatorio";
    public static final String FULL_NAME_REQUIRED = "El campo fullName es obligatorio";
    public static final String EMAIL_REQUIRED = "El campo email es obligatorio";
    public static final String DESCRIPTION_REQUIRED = "El campo description es obligatorio";
    public static final String LAUNCH_DATE_REQUIRED = "El campo launchDate es obligatorio";
    public static final String DURATION_DAY_REQUIRED = "El campo durationDay es obligatorio";
    public static final String DURATION_DAY_INVALID = "El campo durationDay debe ser mayor a 0";
    public static final String CAPABILITY_NAME_INVALID = "Cada capacidad debe tener un nombre válido";
    public static final String CAPABILITY_REQUIRED = "El campo capabilities es una lista y no puede estar vacia";
    public static final String TECHNOLOGY_REQUIRED = "El campo technologies es una lista y no puede estar vacia";
    public static final String TECHNOLOGY_NAME_INVALID = "Cada tecnología debe tener un nombre válido";
    public static final String BOOTCAMP_HISTORY_NOT_FOUND = "Historial del bootcamp no encontrado";

    private DomainErrorMessages() {
    }
}
