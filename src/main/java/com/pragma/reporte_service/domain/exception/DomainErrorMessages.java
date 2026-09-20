package com.pragma.reporte_service.domain.exception;

public final class DomainErrorMessages {

    public static final String BOOTCAMP_ID_REQUIRED = "El campo bootcampId es obligatorio";
    public static final String NAME_REQUIRED = "El campo name es obligatorio";
    public static final String DESCRIPTION_REQUIRED = "El campo description es obligatorio";
    public static final String LAUNCH_DATE_REQUIRED = "El campo launchDate es obligatorio";
    public static final String DURATION_DAY_REQUIRED = "El campo durationDay es obligatorio";
    public static final String DURATION_DAY_INVALID = "El campo durationDay debe ser mayor a 0";
    public static final String CAPACITY_COUNT_REQUIRED = "El campo capacityCount es obligatorio";
    public static final String CAPACITY_COUNT_INVALID = "El campo capacityCount debe ser mayor o igual a 0";
    public static final String TECHNOLOGY_COUNT_REQUIRED = "El campo technologyCount es obligatorio";
    public static final String TECHNOLOGY_COUNT_INVALID = "El campo technologyCount debe ser mayor o igual a 0";
    public static final String CREATED_AT_REQUIRED = "El campo createdAt es obligatorio";
    public static final String BOOTCAMP_HISTORY_NOT_FOUND = "Historial del bootcamp no encontrado";

    private DomainErrorMessages() {
    }
}
