package com.aimprosoft.android.optima.centralizedApp.util.optimaJarUtils;

import com.aimprosoft.android.optima.centralizedApp.app.Constants;

public class IdCampganaUtils {

    static final String ENERGY_SERVICE = "EnergyService";
    static final String GAS_SERVICE = "GasService";
    static final String VOCE_SERVICE = "VoceService";
    static final String ADSL_SERVICE = "ADSLService";
    static final String DEVICE_SERVICE = "DeviceService";

    public enum ServiceType {
        ENERGY(ENERGY_SERVICE),
        GAS(GAS_SERVICE),
        VOCE(VOCE_SERVICE),
        ADSL(ADSL_SERVICE),
        DEVICE(DEVICE_SERVICE);

        private String serviceName;

        ServiceType(String serviceName) {
            this.serviceName = serviceName;
        }

        public String serviceName() {
            return serviceName;
        }
    }

    public static int getOfferIdCampagnaByServiceType(ServiceType serviceType, int configuratorType, boolean isAlreadyClient) {
        int idCampagna = 0;
        switch (serviceType.serviceName) {
            case ENERGY_SERVICE:
                idCampagna = configuratorType == Constants.BUSINESS_CONFIGURATOR ? AbstractJarUtil.ID_CAMPAGNA_ENERGIA_SF : AbstractJarUtil.ID_CAMPAGNA_ENERGIA_SF_CONSUMER;
                break;
            case GAS_SERVICE:
                idCampagna = configuratorType == Constants.BUSINESS_CONFIGURATOR ? AbstractJarUtil.ID_CAMPAGNA_GAS_SF : AbstractJarUtil.ID_CAMPAGNA_GAS_SF_CONSUMER;
                break;
            case VOCE_SERVICE:
                idCampagna = VoceJarUtil.getIdCampagnaAttivazione(configuratorType, isAlreadyClient);
                break;
            case ADSL_SERVICE:
                idCampagna = ADSLJarUtil.getIdCampagnaAttivazione(configuratorType, isAlreadyClient);
                break;
        }
        return idCampagna;
    }
}
