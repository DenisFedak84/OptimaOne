package com.aimprosoft.android.optima.centralizedApp.activity.optilife;


import android.content.Intent;
import android.view.KeyEvent;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.aimprosoft.android.optima.centralizedApp.R;
import com.aimprosoft.android.optima.centralizedApp.app.Constants;
import com.aimprosoft.android.optima.centralizedApp.app.MyApplication;
import com.aimprosoft.android.optima.centralizedApp.db.dao.impl.AssicurazioneOfferDAOImpl;
import com.aimprosoft.android.optima.centralizedApp.db.dao.impl.EnergyOfferDAOImpl;
import com.aimprosoft.android.optima.centralizedApp.db.dao.impl.GasOfferDAOImpl;
import com.aimprosoft.android.optima.centralizedApp.db.dao.impl.InternetOfferDAOImpl;
import com.aimprosoft.android.optima.centralizedApp.db.dao.impl.MobileOfferDAOImpl;
import com.aimprosoft.android.optima.centralizedApp.db.dao.impl.OfferDAOImpl;
import com.aimprosoft.android.optima.centralizedApp.db.dao.impl.OfferDeviceDAOImpl;
import com.aimprosoft.android.optima.centralizedApp.db.dao.impl.TLCOfferDAOImpl;
import com.aimprosoft.android.optima.centralizedApp.db.dao.impl.WlrOfferDetailsDAOImpl;
import com.aimprosoft.android.optima.centralizedApp.db.dao.impl.WlrOffersDAOImpl;
import com.aimprosoft.android.optima.centralizedApp.db.entity.AssicurazioneOffer;
import com.aimprosoft.android.optima.centralizedApp.db.entity.BaseEntity;
import com.aimprosoft.android.optima.centralizedApp.db.entity.EnergyOffer;
import com.aimprosoft.android.optima.centralizedApp.db.entity.GasOffer;
import com.aimprosoft.android.optima.centralizedApp.db.entity.InternetOffer;
import com.aimprosoft.android.optima.centralizedApp.db.entity.MobileOffer;
import com.aimprosoft.android.optima.centralizedApp.db.entity.Offer;
import com.aimprosoft.android.optima.centralizedApp.db.entity.OfferDevice;
import com.aimprosoft.android.optima.centralizedApp.db.entity.TLCOffer;
import com.aimprosoft.android.optima.centralizedApp.db.entity.WlrOfferDetails;
import com.aimprosoft.android.optima.centralizedApp.db.entity.WlrOffers;
import com.aimprosoft.android.optima.centralizedApp.service.AbstractService;
import com.aimprosoft.android.optima.centralizedApp.service.EliminiaEnergyOffer;
import com.aimprosoft.android.optima.centralizedApp.service.EliminiaGasOffer;
import com.aimprosoft.android.optima.centralizedApp.service.EliminiaInternetOffer;
import com.aimprosoft.android.optima.centralizedApp.service.EliminiaMobileOffer;
import com.aimprosoft.android.optima.centralizedApp.service.EliminiaTLCOffer;
import com.aimprosoft.android.optima.centralizedApp.service.EliminiaWlrOffer;
import com.aimprosoft.android.optima.centralizedApp.service.OfferUpdate;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Modifica extends BaseActivity {
    private Offer offer = MyApplication.get("offerEntity", Offer.class);
    private int year;
    private int month;
    private int day;

    private EnergyOfferDAOImpl energyOfferDAO = new EnergyOfferDAOImpl();
    private EnergyOffer currentEnergyOffer;
    private GasOffer currentGasOffer;
    private TLCOffer currentTLCOffer;
    private WlrOffers currentWlrOffers;
    private InternetOffer currentInternetOffer;
    private MobileOffer currentMobileOffer;
    private GasOfferDAOImpl gasOfferDAO = new GasOfferDAOImpl();
    private LinearLayout devicesLayout;
    private LinearLayout assicurazioneLayout;
    private OfferDeviceDAOImpl offerDeviceDAO = new OfferDeviceDAOImpl();

    public void onCreate(android.os.Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.modifica_layout_optima);
        devicesLayout = (LinearLayout) findViewById(R.id.modificaDevices);
        assicurazioneLayout = (LinearLayout) findViewById(R.id.modificaAssicurazione);

        init();
        Calendar c = Calendar.getInstance();
        year = c.get(Calendar.YEAR);
        month = c.get(Calendar.MONTH);
        day = c.get(Calendar.DAY_OF_MONTH);

        updateOffer(offer);

        findViewById(R.id.elettricaModificaButton).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MyApplication.set("modificaElettricitiLable", true);
                startChildActivity(currentEnergyOffer.isQuestionarioUsed() ? EnergeticiQuestionario.class : EnergeticiLaFattura.class);
            }
        });

        findViewById(R.id.gasModificaButton).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MyApplication.set("modificaGasLable", true);
                startChildActivity(currentGasOffer.isQuestionarioUsing() ? GasQuestionario.class : Gas.class);
            }
        });

        findViewById(R.id.tlcModificaButton).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MyApplication.set("modificaTLCLable", true);
                startChildActivity(TLC.class);
            }
        });

        findViewById(R.id.internetModificaButton).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MyApplication.set("modificaInternetLable", true);
                startChildActivity(TLC.class);
            }
        });

        findViewById(R.id.deviceModificaButton).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MyApplication.set("modificaDeviceLable", true);
                startChildActivity(DevicePage.class);
            }
        });

        findViewById(R.id.assicurazioneModificaButton).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MyApplication.set("modificaAssicurazioneLable", true);
                startChildActivity(AssicurazionePage.class);
            }
        });

        findViewById(R.id.mobileModificaButton).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MyApplication.set("modificaMobileLable", true);
                startChildActivity(MobilePage.class);
            }
        });
    }

    private void updateOffer(Offer offer) {
        if (offer != null) {
            updateDisplayDate();
            fillValueMap();
            new OfferDAOImpl().update(offer);

            checkForAggiungiServisio();
            checkForeDevicesPresent();
            checkForeAssicurzionePresent();
            showCompleteOffer(R.id.energiaElettricaModifica, R.id.gasMetanoModifica, R.id.telefoniaFissaModifica,
                    R.id.internetModifica, R.id.mobileModifica);

            if (offer.getEnergyOfferId() != null) {
                TextView kwl = (TextView) findViewById(R.id.elettricitaKWLModifica);
                kwl.setText(getEnergyOfferKwl(energyOfferDAO.getEnergyOfferById(offer.getEnergyOfferId())));
            }
            if (offer.getGasOfferId() != null) {
                TextView smc = (TextView) findViewById(R.id.gasSmcModifica);
                smc.setText(getGasOfferSmc(gasOfferDAO.getGasOfferById(offer.getGasOfferId())));
            }
        }
    }

    private void checkForeAssicurzionePresent() {
        assicurazioneLayout.setVisibility(offer != null &&
                (offer.getEnergyOfferId() != null ||
                        offer.getGasOfferId() != null)
                && !new AssicurazioneOfferDAOImpl().getAssicurazioneByOfferId(offer.getOfferId()).isEmpty()
                ? View.VISIBLE : View.GONE);
    }

    private void init() {
        if (offer != null && offer.getConfiguratorType() == Constants.CONSUMER_CONFIGURATOR && isShowInformazioniAbitazioniQuestionario()) {
            findViewById(R.id.informazioniAbitazione).setVisibility(View.VISIBLE);
        }

        findViewById(R.id.informazioniModificaButton).
                setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        MyApplication.set("modificaInformazioniAbitazione", true);
                        startChildActivity(TipologiaOfferta.class);
                    }
                });

        findViewById(R.id.anagraficaModificaButton).
                setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        MyApplication.set("offerEntity", offer);
                        Intent intent = new Intent(Modifica.this, MainActivity.class);
                        intent.putExtra(Constants.MODIFICA_ANAGRAFICA, true);
                        startActivity(intent);
                    }
                });
    }

    private void updateDisplayDate() {
        Calendar c = Calendar.getInstance();
        year = c.get(Calendar.YEAR);
        month = c.get(Calendar.MONTH);
        day = c.get(Calendar.DAY_OF_MONTH);
        String creationDate = pad(day) + "/" + pad(month + 1) + "/" + year;
        offer.setCreationDate(creationDate);
    }

    private void checkForeDevicesPresent() {
        devicesLayout.setVisibility(offer.getConfiguratorType() == Constants.BUSINESS_CONFIGURATOR &&
                (offer.getEnergyOfferId() != null || offer.getGasOfferId() != null) ? View.VISIBLE : View.GONE);
    }

    private void checkForAssicurazionePresent() {
        devicesLayout.setVisibility(offer.getConfiguratorType() == Constants.BUSINESS_CONFIGURATOR &&
                (offer.getEnergyOfferId() != null || offer.getGasOfferId() != null) ? View.VISIBLE : View.GONE);
    }

    private void checkOfferDevicesStillActual() {
        if (offer != null && offer.getGasOfferId() == null && offer.getEnergyOfferId() == null) {
            devicesLayout.setVisibility(View.GONE);
            removeDeviceCost();
            offerDeviceDAO.deleteOfferDeviceByOfferId(offer.getOfferId());
        }
    }

    private void removeDeviceCost() {
        List<OfferDevice> offerDeviceList = new OfferDeviceDAOImpl().getSortedDevicesByOfferId(offer.getOfferId(), true);
        BigDecimal sumOfferCost = offer.getOfferCost();
        BigDecimal sumOfferCostIva = offer.getOfferCostIVA();
        for (OfferDevice device : offerDeviceList) {
            sumOfferCost = sumOfferCost.subtract(new BigDecimal(device.getDeviceCost()));
            sumOfferCostIva = sumOfferCostIva.subtract(new BigDecimal(device.getDeviceCostIva()));
        }

        offer.setOfferCost(sumOfferCost);
        offer.setOfferCostIVA(sumOfferCostIva);
    }

    private String pad(Integer d) {
        return d.toString().length() == 1 ? "0" + d.toString() : d.toString();
    }

    public void startAggiungiServizio(View v) {
        startChildActivity(AggiungiServizio.class);
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        return keyCode != 4 && super.onKeyDown(keyCode, event);
    }

    public void deleteEnergyOfferRow(View v) {
        if (offer != null) {
            findViewById(R.id.energiaElettricaModifica).setVisibility(View.GONE);
            if (currentEnergyOffer != null) {
                recalculateOfferCost(currentEnergyOffer.getOfferCost(), currentEnergyOffer.getOfferCostIVA());
            }
            offer.setEnergyOfferId(null);

            checkAssicurazioneAvailability();
            MyApplication.set("offerEntity", offer);
            List<BaseEntity> offers = new ArrayList<>();
            offers.add(offer);
            checkOfferDevicesStillActual();
            new OfferUpdate(this, null).execute(offers);

            new EliminiaEnergyOffer(this, new AbstractService.OnTaskCompleted() {
                @Override
                public void onTaskCompleted(AbstractService service) {
                    Toast.makeText(Modifica.this, "Informazioni è stato rimosso", Toast.LENGTH_SHORT).show();
                    MyApplication.remove("elettricitiFlag");
                }
            }).execute(currentEnergyOffer);

            checkForAggiungiServisio();
            MyApplication.remove("energyOfferEntity");
            MyApplication.remove("ArchivioOfferEntity");
            MyApplication.remove("elettricitiFlag");
        }
    }

    public void deleteAssicurazioneOfferRow(View view) {
        if (offer != null) {
            substractAssicurazione();
        }
    }

    private void checkAssicurazioneAvailability() {
        if (offer != null && offer.getEnergyOfferId() == null && offer.getGasOfferId() == null) {
            substractAssicurazione();
        }
    }

    private void substractAssicurazione() {
        AssicurazioneOfferDAOImpl assicurazioneOfferDAO = new AssicurazioneOfferDAOImpl();
        List<AssicurazioneOffer> assicurazioneOffers = assicurazioneOfferDAO.getAssicurazioneByOfferId(offer.getOfferId());

        double assicurazioneCost = 0.0;
        double assicurazioneCostIva = 0.0;
        if (!assicurazioneOffers.isEmpty()) {
            for (AssicurazioneOffer assicurazioneOffer : assicurazioneOffers) {
                double monthlyAssicurazioneCost = assicurazioneOffer.getAssicurazioneCost() / 12;
                assicurazioneCost += monthlyAssicurazioneCost + assicurazioneOffer.getCostoGestioneIntegrata();
                assicurazioneCostIva += monthlyAssicurazioneCost + assicurazioneOffer.getCostoGestioneIntegrataIva();
            }
            offer.setOfferCost(offer.getOfferCost().subtract(new BigDecimal(assicurazioneCost)));
            offer.setOfferCostIVA(offer.getOfferCostIVA().subtract(new BigDecimal(assicurazioneCostIva)));
            assicurazioneOfferDAO.deleteAssicurazioneByOfferId(offer.getOfferId());
            assicurazioneLayout.setVisibility(View.GONE);
        }
    }

    public void deleteGasOfferRow(View v) {
        if (offer != null) {
            findViewById(R.id.gasMetanoModifica).setVisibility(View.GONE);
            if (currentGasOffer != null) {
                recalculateOfferCost(currentGasOffer.getOfferCost(), currentGasOffer.getOfferCostIVA());
            }
            offer.setGasOfferId(null);
            checkAssicurazioneAvailability();

            List<BaseEntity> offers = new ArrayList<>();
            offers.add(offer);
            MyApplication.set("offerEntity", offer);
            checkOfferDevicesStillActual();

            new OfferUpdate(this, null).execute(offers);

            new EliminiaGasOffer(this, new AbstractService.OnTaskCompleted() {
                @Override
                public void onTaskCompleted(AbstractService service) {
                    Toast.makeText(Modifica.this, "Informazioni è stato rimosso", Toast.LENGTH_SHORT).show();
                    MyApplication.remove("gasFlag");
                }
            }).execute(currentGasOffer);

            checkForAggiungiServisio();
            MyApplication.remove("GasOffer");
            MyApplication.remove("ArchivioGasOffer");
            MyApplication.remove("gasFlag");
        }
    }

    public void deleteTLCOfferRow(View v) {
        if (offer != null) {
            findViewById(R.id.telefoniaFissaModifica).setVisibility(View.GONE);
            if (offer.getWlrOfferId() != null) deleteWlrInfo(false);
            if (offer.getTlcOfferId() != null) deleteTlcOfferInfo();
        }
    }

    private void deleteTlcOfferInfo() {
        TLCOffer tlcOffer = new TLCOfferDAOImpl().getTLCOfferById(offer.getTlcOfferId());
        recalculateOfferCost(tlcOffer.getBundleCost(), tlcOffer.getBundleCostIVA());
        offer.setTlcOfferId(null);
        MyApplication.set("offerEntity", offer);
        List<BaseEntity> offers = new ArrayList<>();
        offers.add(offer);
        new OfferUpdate(this, null).execute(offers);
        new EliminiaTLCOffer(this, new AbstractService.OnTaskCompleted() {
            @Override
            public void onTaskCompleted(AbstractService service) {
                Toast.makeText(Modifica.this, "Informazioni è stato rimosso", Toast.LENGTH_SHORT).show();
                MyApplication.remove("TLCFlag");
                MyApplication.remove("TLCOffer");
            }
        }).execute(currentTLCOffer);
        checkForAggiungiServisio();
        MyApplication.remove("navigationMap");
        MyApplication.remove("TLC");
        MyApplication.remove("ArchivioTLCOffer");
        MyApplication.remove("TLCFlag");
        fillValueMap();
    }

    private void deleteWlrInfo(boolean specialMessage) {
        WlrOffers wlrOffers = new WlrOffersDAOImpl().getWlrOfferById(offer.getWlrOfferId());
        recalculateOfferCost(BigDecimal.valueOf(wlrOffers.getOfferCost()), BigDecimal.valueOf(wlrOffers.getOfferCostIVA()));
        offer.setWlrOfferId(null);
        MyApplication.set("offerEntity", offer);
        List<BaseEntity> offers = new ArrayList<>();
        offers.add(offer);
        new OfferUpdate(this, null).execute(offers);

        new EliminiaWlrOffer(this, new AbstractService.OnTaskCompleted() {
            @Override
            public void onTaskCompleted(AbstractService service) {
                String message = specialMessage ? "Eliminando il servizio ADSL anche il servizio VOIP sarà eliminato" : "Informazioni è stato rimosso";
                Toast.makeText(Modifica.this, message, Toast.LENGTH_SHORT).show();
                MyApplication.remove("WlrOffer");
            }
        }).execute(wlrOffers);

        checkForAggiungiServisio();
        MyApplication.remove("navigationMap");
        MyApplication.remove("ArchivioWlrOffer");
        MyApplication.remove("TLCFlag");
        fillValueMap();
    }

    public void deleteInternetOfferRow(View v) {
        if (offer != null && currentInternetOffer != null) {
            findViewById(R.id.internetModifica).setVisibility(View.GONE);
            InternetOffer internetOffer = new InternetOfferDAOImpl().getInternetOfferById(offer.getInternetOfferId());
            if (internetOffer != null) {
                recalculateOfferCost(new BigDecimal(internetOffer.getCost()), new BigDecimal(internetOffer.getCostIVA()));
            }
            offer.setInternetOfferId(null);
            List<BaseEntity> offers = new ArrayList<>();
            offers.add(offer);

            MyApplication.set("offerEntity", offer);
            new OfferUpdate(this, null).execute(offers);
            boolean isVoip = isVoipPresent();
            new EliminiaInternetOffer(this, new AbstractService.OnTaskCompleted() {
                @Override
                public void onTaskCompleted(AbstractService service) {
                    if (!isVoip) {
                        Toast.makeText(Modifica.this, "Informazioni è stato rimosso", Toast.LENGTH_SHORT).show();
                    } else {
                        deleteWlrInfo(true);
                        findViewById(R.id.telefoniaFissaModifica).setVisibility(View.GONE);
                    }
                    MyApplication.remove("InternetOffer");
                    MyApplication.remove("InternetFlag");
                }
            }).execute(currentInternetOffer);
            checkForAggiungiServisio();
            MyApplication.remove("navigationMap");
            MyApplication.remove("Internet");
            MyApplication.remove("ArchivioInternetOffer");
            MyApplication.remove("InternetFlag");
            fillValueMap();
        }
    }

    private boolean isVoipPresent() {
        boolean result = false;
        if (currentWlrOffers != null) {
            List<WlrOfferDetails> wlrOfferDetailsList = new WlrOfferDetailsDAOImpl().getWlrOfferDetailsByWlrOfferId(currentWlrOffers.getWlrOfferId(), true);
            for (WlrOfferDetails wlrOfferDetails : wlrOfferDetailsList) {
                if (wlrOfferDetails.getRete().equals(Constants.VOIP)) {
                    result = true;
                    break;
                }
            }
        }
        return result;
    }

    public void deleteMobileOfferRow(View v) {
        if (offer != null && currentMobileOffer != null) {
            findViewById(R.id.mobileModifica).setVisibility(View.GONE);
            offer.setMobileOfferId(null);
            List<BaseEntity> offers = new ArrayList<>();
            offers.add(offer);
            MyApplication.set("offerEntity", offer);
            new OfferUpdate(this, null).execute(offers);
            new EliminiaMobileOffer(this, new AbstractService.OnTaskCompleted() {
                @Override
                public void onTaskCompleted(AbstractService service) {
                    Toast.makeText(Modifica.this, "Informazioni è stato rimosso", Toast.LENGTH_SHORT).show();
                }
            }).execute(currentMobileOffer);
            checkForAggiungiServisio();
            fillValueMap();
        }
    }

//    public void deleteMobileOfferRow(View v) {
//        findViewById(R.id.mobileModifica).setVisibility(View.GONE);
//        MobileOffer mobileOffer = new MobileOfferDAOImpl().getMobileOfferById(offer.getMobileOfferId());
//        offer.setMobileOfferId(null);
//        new OfferUpdate(this, null).execute(offers);
//        List<BaseEntity> offers = new ArrayList<>();
//        offers.add(offer);
//        Deamon.set("offerEntity", offer);
//        updateOfferTask.setParam(offers);
//        asyncTaskManager.setupTask(updateOfferTask);
//        EliminiaMobileOffer deleteMobileOfferTask = new EliminiaMobileOffer(new OnTaskCompleteCallBack() {
//            @Override
//            public void onTaskComplete(Task task) {
//                showMessage("Informazioni è stato rimosso");
//                Deamon.remove("InternetOffer");
//                Deamon.remove("InternetFlag");
//            }
//        });
//        deleteMobileOfferTask.setParam(currentMobileOffer);
//        asyncTaskManager.setupTask(deleteMobileOfferTask);
//        checkForAggiungiServisio();
//        Deamon.remove("navigationMap");
//        Deamon.remove("Mobile");
//        Deamon.remove("ArchivioMobileOffer");
//        Deamon.remove("MobileFlag");
//        fillValueMap();
//    }

    private void checkForAggiungiServisio() {
        if (offer != null && offer.getEnergyOfferId() == null
                | offer.getGasOfferId() == null
                | offer.getInternetOfferId() == null
                | (offer.getTlcOfferId() == null && offer.getWlrOfferId() == null)
                | offer.getMobileOfferId() == null) {
            findViewById(R.id.aggiungiButton).setVisibility(View.VISIBLE);
        } else {
            findViewById(R.id.aggiungiButton).setVisibility(View.GONE);
        }
    }

    private void recalculateOfferCost(BigDecimal currentOfferCost, BigDecimal currentOfferCostIva) {
        BigDecimal sumOfferCost = offer.getOfferCost();
        offer.setOfferCost(sumOfferCost.subtract(currentOfferCost));
        BigDecimal sumOfferCostIva = offer.getOfferCostIVA();
        offer.setOfferCostIVA(sumOfferCostIva.subtract(currentOfferCostIva));
        offer.setConto_relax(0);
    }

    private void fillValueMap() {
        if (offer != null) {
            Map<Integer, Class> servizioNavigationMap = new HashMap<>();
            if (offer.getEnergyOfferId() != null) {
                currentEnergyOffer = energyOfferDAO.getEnergyOfferById(offer.getEnergyOfferId());
                MyApplication.set("navigationMap", servizioNavigationMap);
                MyApplication.set("energyOfferEntity", currentEnergyOffer);
                MyApplication.set("ArchivioOfferEntity", currentEnergyOffer);
                MyApplication.set("elettricitiFlag", true);
            }

            if (offer.getGasOfferId() != null) {
                currentGasOffer = gasOfferDAO.getGasOfferById(offer.getGasOfferId());
                MyApplication.set("navigationMap", servizioNavigationMap);
                MyApplication.set("GasOffer", currentGasOffer);
                MyApplication.set("ArchivioGasOffer", currentGasOffer);
                MyApplication.set("gasFlag", true);
            }

            if (offer.getTlcOfferId() != null) {
                currentTLCOffer = new TLCOfferDAOImpl().getTLCOfferById(offer.getTlcOfferId());
                servizioNavigationMap.put(5, TLC.class);
                MyApplication.set("navigationMap", servizioNavigationMap);
                MyApplication.set("TLC", currentTLCOffer);
                MyApplication.set("ArchivioTLCOffer", currentTLCOffer);
                MyApplication.set("TLCFlag", true);
            }

            if (offer.getWlrOfferId() != null) {
                currentWlrOffers = new WlrOffersDAOImpl().getWlrOfferById(offer.getWlrOfferId());
                servizioNavigationMap.put(5, TLC.class);
                MyApplication.set("navigationMap", servizioNavigationMap);
                MyApplication.set("WlrOffer", currentWlrOffers);
                MyApplication.set("ArchivioWlrOffer", currentWlrOffers);
                MyApplication.set("TLCFlag", true);
            }

            if (offer.getInternetOfferId() != null) {
                currentInternetOffer = new InternetOfferDAOImpl().getInternetOfferById(offer.getInternetOfferId());
                servizioNavigationMap.put(4, TLC.class);
                MyApplication.set("navigationMap", servizioNavigationMap);
                MyApplication.set("Internet", currentInternetOffer);
                MyApplication.set("ArchivioInternetOffer", currentInternetOffer);
                MyApplication.set("InternetFlag", true);
            }

            if (offer.getMobileOfferId() != null) {
                currentMobileOffer = new MobileOfferDAOImpl().getMobileOfferById(offer.getMobileOfferId());
            }

//        if (offer != null && offer.getMobileOfferId() != null) {
//            currentMobileOffer = new MobileOfferDAOImpl().getMobileOfferById(offer.getMobileOfferId());
//            canone += currentMobileOffer.getOfferCost();
//            canoneIVA += currentMobileOffer.getOfferCost();
//            servizioNavigationMap.put(3, TLC.class);
//            Deamon.set("navigationMap", servizioNavigationMap);
//            Deamon.set("Mobile", currentMobileOffer);
//            Deamon.set("ArchivioMobileOffer", currentMobileOffer);
//            Deamon.set("MobileFlag", true);
//        }
        }
    }

    private boolean isShowInformazioniAbitazioniQuestionario() {
        return (isGasOfferNotQuestionarioUsed() || isEnergyOfferNotQuestionarioUsed());
    }

    private boolean isGasOfferNotQuestionarioUsed() {
        return offer != null && offer.getGasOfferId() != null && gasOfferDAO.getGasOfferById(offer.getGasOfferId()).isQuestionarioUsing();
    }

    private boolean isEnergyOfferNotQuestionarioUsed() {
        return offer != null && offer.getEnergyOfferId() != null && energyOfferDAO.getEnergyOfferById(offer.getEnergyOfferId()).isQuestionarioUsed();
    }

    @Override
    public void calcola(View view) {
        Intent intent = new Intent(this, Canone.class);
        intent.putExtra("ChoiceCode", 1);
        startActivity(intent);
    }

    @Override
    public void closeAll(final View v) {
        Modifica.super.closeAll(v);
    }
}