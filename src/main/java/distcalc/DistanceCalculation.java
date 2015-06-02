package distcalc;

import javax.json.Json;
import javax.json.JsonArray;
import javax.json.JsonObject;
import javax.ws.rs.*;

@Path("/prscalc")
public class DistanceCalculation {
     @GET
     @Path("/rmobile")
     @Produces("application/json;charset=UTF-8")
     public JsonObject getDistanceForMobileStation(
             @QueryParam("name") String lineName,
             @QueryParam("upmob") int uPMob,
             @QueryParam("upstat") int uPStat,
             @QueryParam("uminmob") int uMinMob,
             @QueryParam("uminstat") int uMinStat,
             @QueryParam("aper") int aPer,
             @QueryParam("apertunnel") int aPerTunnel,
             @QueryParam("an") double aN,
             @QueryParam("antunnel") double aNTunnel,
             @QueryParam("av") double aV,
             @QueryParam("ku") int Ku,
             @QueryParam("aon") int aon,
             @QueryParam("atr") double atr,
             @DefaultValue("false") @QueryParam("tunnel") boolean tunnel,
             @QueryParam("speed") int speed,
             @QueryParam("conductdist") int dist,
             @QueryParam("load") int load,
             @QueryParam("linequantity") int lineQuantity,
             @QueryParam("feederlength") double feederLength,
             @DefaultValue("false") @QueryParam("anker") boolean anker,
             @DefaultValue("cable") @QueryParam("crosstype") String crossType,
             @QueryParam("crossquantity") int crossQuantity,
             @QueryParam("transformators") int transfQuantity,
             @DefaultValue("true") @QueryParam("loco") boolean loco
     ){
         LineType lt = new LineType(lineName, uPMob, uPStat, uMinMob, uMinStat, aPer, aPerTunnel, aN, aNTunnel, aV, Ku, aon, atr);
         double rMobile = calcRmob(lt, load, speed, dist, lineQuantity, tunnel, feederLength, anker, crossQuantity, crossType, transfQuantity, loco);
         JsonObject mobileReport = Json.createObjectBuilder()
                 .add("description", "Дальность для мобильной станции: ")
                 .add("value", rMobile)
                 .build();
         return mobileReport;
     }

     @GET
     @Path("/rstationary")
     @Produces("application/json;charset=UTF-8")
     public JsonObject getDistanceForStationaryStation(
             @QueryParam("name") String lineName,
             @QueryParam("upmob") int uPMob,
             @QueryParam("upstat") int uPStat,
             @QueryParam("uminmob") int uMinMob,
             @QueryParam("uminstat") int uMinStat,
             @QueryParam("aper") int aPer,
             @QueryParam("apertunnel") int aPerTunnel,
             @QueryParam("an") double aN,
             @QueryParam("antunnel") double aNTunnel,
             @QueryParam("av") double aV,
             @QueryParam("ku") int Ku,
             @QueryParam("aon") int aon,
             @QueryParam("atr") double atr,
             @DefaultValue("false") @QueryParam("tunnel") boolean tunnel,
             @QueryParam("conductdist") int dist,
             @QueryParam("load") int load,
             @QueryParam("linequantity") int lineQuantity,
             @QueryParam("feederlength") double feederLength,
             @DefaultValue("false") @QueryParam("anker") boolean anker,
             @DefaultValue("cable") @QueryParam("crosstype") String crossType,
             @QueryParam("crossquantity") int crossQuantity,
             @QueryParam("transformators") int transfQuantity,
             @DefaultValue("true") @QueryParam("loco") boolean loco
     ){
         LineType lt = new LineType(lineName, uPMob, uPStat, uMinMob, uMinStat, aPer, aPerTunnel, aN, aNTunnel, aV, Ku, aon, atr);
         double rStationary = calcRstat(lt, load, dist, lineQuantity, tunnel, feederLength, anker, crossQuantity, crossType, transfQuantity, loco);
         JsonObject stationaryReport = Json.createObjectBuilder()
                 .add("description", "Дальность для стационарной станции: ")
                 .add("value", rStationary)
                 .build();
         return stationaryReport;
     }
    @GET
    @Path("/intermediate")
    @Produces("application/json;charset=UTF-8")
    public JsonArray getIntermediateCalculationResults(
            @QueryParam("name") String lineName,
            @QueryParam("upmob") int uPMob,
            @QueryParam("upstat") int uPStat,
            @QueryParam("uminmob") int uMinMob,
            @QueryParam("uminstat") int uMinStat,
            @QueryParam("aper") int aPer,
            @QueryParam("apertunnel") int aPerTunnel,
            @QueryParam("an") double aN,
            @QueryParam("antunnel") double aNTunnel,
            @QueryParam("av") double aV,
            @QueryParam("ku") int Ku,
            @QueryParam("aon") int aon,
            @QueryParam("atr") double atr,
            @DefaultValue("false") @QueryParam("tunnel") boolean tunnel,
            @QueryParam("speed") int speed,
            @QueryParam("conductdist") int dist,
            @QueryParam("load") int load,
            @QueryParam("linequantity") int lineQuantity,
            @QueryParam("feederlength") double feederLength,
            @DefaultValue("false") @QueryParam("anker") boolean anker,
            @DefaultValue("cable") @QueryParam("crosstype") String crossType,
            @QueryParam("crossquantity") int crossQuantity,
            @QueryParam("transformators") int transfQuantity,
            @DefaultValue("true") @QueryParam("loco") boolean loco
    ){
        LineType lt = new LineType(lineName, uPMob, uPStat, uMinMob, uMinStat, aPer, aPerTunnel, aN, aNTunnel, aV, Ku, aon, atr);
        int Aprd = calcAprd(load);
        double uMinMobile = calcUminMob(lt, speed);
        double uMinStation = calcUminStat(lt);
        double Aper = calcAper(lt, dist, lineQuantity, tunnel);
        double ast = calcAst(lt, feederLength, anker);
        double alin = calcAlin(lt, crossQuantity, crossType, transfQuantity);
        double alok = calcAlok(loco);
        double an = calcAn(lt, tunnel);
        JsonArray intermediateReport = Json.createArrayBuilder()
                .add(Json.createObjectBuilder()
                        .add("description", "Расчет дальности эффективной связи для линий: ")
                        .add("value", lineName)
                )
                .add(Json.createObjectBuilder()
                        .add("description", "Исходные данные:")
                )
                .add(Json.createObjectBuilder()
                        .add("description", "Up моб. = ")
                        .add("value", uPMob)
                )
                .add(Json.createObjectBuilder()
                        .add("description", "Up стат. = ")
                        .add("value", uPStat)
                )
                .add(Json.createObjectBuilder()
                        .add("description", "А пер. = ")
                        .add("value", Aper)
                )
                .add(Json.createObjectBuilder()
                        .add("description", "А пер.(тоннель) = ")
                        .add("value", aPerTunnel)
                )
                .add(Json.createObjectBuilder()
                        .add("description", "An = ")
                        .add("value", aN)
                )
                .add(Json.createObjectBuilder()
                        .add("description", "An(тоннель) = ")
                        .add("value", aNTunnel)
                )
                .add(Json.createObjectBuilder()
                        .add("description", "Av = ")
                        .add("value", aV)
                )
                .add(Json.createObjectBuilder()
                        .add("description", "Ku = ")
                        .add("value", Ku)
                )
                .add(Json.createObjectBuilder()
                        .add("description", "Aon = ")
                        .add("value", aon)
                )
                .add(Json.createObjectBuilder()
                        .add("description", "Тоннель = ")
                        .add("value", tunnel)
                )
                .add(Json.createObjectBuilder()
                        .add("description", "Скорость = ")
                        .add("value", speed)
                )
                .add(Json.createObjectBuilder()
                        .add("description", "Расстояние между проводами линии = ")
                        .add("value", dist)
                )
                .add(Json.createObjectBuilder()
                        .add("description", "Число нагрузок на линию = ")
                        .add("value", load)
                )
                .add(Json.createObjectBuilder()
                        .add("description", "Число путей = ")
                        .add("value", lineQuantity)
                )
                .add(Json.createObjectBuilder()
                        .add("description", "Длина фидера = ")
                        .add("value", feederLength)
                )
                .add(Json.createObjectBuilder()
                        .add("description", "Возбуждение линии в месте анкеровки = ")
                        .add("value", anker)
                )
                .add(Json.createObjectBuilder()
                        .add("description", "Тип перехода = ")
                        .add("value", crossType)
                )
                .add(Json.createObjectBuilder()
                        .add("description", "Число переходов = ")
                        .add("value", crossQuantity)
                )
                .add(Json.createObjectBuilder()
                        .add("description", "Число трансформаторов = ")
                        .add("value", transfQuantity)
                )
                .add(Json.createObjectBuilder()
                        .add("description", "Использование электровоза/тепловоза = ")
                        .add("value", loco)
                )
                .add(Json.createObjectBuilder()
                        .add("description", "Результаты расчета:")
                )
                .add(Json.createObjectBuilder()
                        .add("description", "А прд. = ")
                        .add("value", Aprd)
                )
                .add(Json.createObjectBuilder()
                        .add("description", "Umin моб. = ")
                        .add("value", uMinMobile)
                )
                .add(Json.createObjectBuilder()
                        .add("description", "Umin стат. = ")
                        .add("value", uMinStation)
                )
                .add(Json.createObjectBuilder()
                        .add("description", "А ст. = ")
                        .add("value", ast)
                )
                .add(Json.createObjectBuilder()
                        .add("description", "А лин. = ")
                        .add("value", alin)
                )
                .add(Json.createObjectBuilder()
                        .add("description", "А лок. = ")
                        .add("value", alok)
                )
                .build();
        return intermediateReport;
    }
    /*
    Расчет дальности связи r для возимой станции.
     */
    protected double calcRmob(LineType lt, int load, int speed, int dist, int lineQuantity, boolean tunnel, double feederLength, boolean anker, int n, String crossType, int m, boolean loco){
        int Aprd = calcAprd(load);
        double uMinMob = calcUminMob(lt, speed);
        double Aper = calcAper(lt, dist, lineQuantity, tunnel);
        double ast = calcAst(lt, feederLength, anker);
        double alin = calcAlin(lt, n, crossType, m);
        double alok = calcAlok(loco);
        double an = calcAn(lt, tunnel);
        double rMob = (Aprd - uMinMob - Aper - ast - alin - alok)/an;
        return rMob;
    }
    /*
    Расчет дальности связи r для стационарой станции.
     */
    protected double calcRstat(LineType lt, int load, int dist, int lineQuantity, boolean tunnel, double feederLength, boolean anker, int n, String crossType, int m, boolean loco){
        int Aprd = calcAprd(load);
        double uMinStat = calcUminStat(lt);
        double Aper = calcAper(lt, dist, lineQuantity, tunnel);
        double ast = calcAst(lt, feederLength, anker);
        double alin = calcAlin(lt, n, crossType, m);
        double alok = calcAlok(loco);
        double an = calcAn(lt, tunnel);
        double rStat = (Aprd - uMinStat - Aper - ast - alin - alok)/an;
        return rStat;
    }

    /*
    Число нагрузок на линию load задается пользователем (1 или 2?).
     */
    protected int calcAprd(int load){
        if (load == 1) return 148;
        else return 145;
    }

    /*
    Расчет u min для возимой станции.
    Переменная скорости поезда speed задается пользователем.
     */
    protected double calcUminMob(LineType lt, int speed){
        double uP;
        if (speed > 140) uP = lt.getuPMob() + 3.5;
        else uP = lt.getuPMob();
        int Kdob = 6;
        int Ku = lt.getKu();
        double uMinMob = uP + Kdob + Ku;
        return uMinMob;
    }

    /*
    Расчет u min для стационарной станции.
     */
    protected int calcUminStat(LineType lt){
        int uP = lt.getuPStat();
        int Kdob = 6;
        int Ku = lt.getKu();
        int uMinStat = uP + Kdob + Ku;
        return uMinStat;
    }

    /*
    Переменная расстояния между проводами dist задается пользователем (>10м).
    Переменная количества путей lineQuantity задается пользователем (1 либо иное значение?).
    Переменная tunnel должна быть установлена в true, если нужны значения для тоннелей; задается пользователем.
     */
    protected double calcAper(LineType lt, int dist, int lineQuantity, boolean tunnel){
        double Aper;
        if (lt.getName().toLowerCase().contains("влс")){
            if ((dist != 25) && (dist > 10)){
                if(tunnel) Aper = lt.getaPerTunnel() + 0.5*(dist - 25);
                else Aper = lt.getaPer() + 0.5*(dist - 25);
            }
            else if (dist == 25){
                if (tunnel) Aper = lt.getaPerTunnel();
                else Aper = lt.getaPer();
            }
            else{
                throw new NumberFormatException();
            }
        }
        else{
            if(tunnel) Aper = lt.getaPerTunnel();
            else Aper = lt.getaPer();
        }
        if(lineQuantity == 1) Aper -= 4;
        return Aper;
    }

    /*
    Длина фидера feederLength задается пользователем.
    Переменная anker должна быть установлена в true, если линия возбуждается в месте анкеровки... мда :| задается пользователем.
     */
    protected double calcAst(LineType lt, double feederLength, boolean anker){
        double af = 0.008;
        double asu = 1.5;
        double av = lt.getaV();
        int aon = lt.getAon();
        int Kp;
        if (anker) Kp = 0;
        else Kp = 3;
        double ast = af*feederLength + asu + av + aon + Kp;
        return ast;
    }

    /*
    Число переходов n задается пользователем.
    Тип перехода crossType задается пользователем (air или cable).
    Число трансформаторов m задается пользователем.
     */
    protected double calcAlin(LineType lt, int n, String crossType, int m){
        int amn = 1;
        int ap = 1;
        double ad = 2.5;
        double an;
        if (crossType.equals("air")) an = 0.7;
        else an = 2.5;
        double atr = lt.getAtr();
        double alin = amn + ap + ad + n*an + m*atr;
        return alin;
    }

    /*
    Переменная loco должна быть установлена в true, если рассматривается электровоз/тепловоз; задается пользователем.
     */
    protected double calcAlok(boolean loco){
        double asu = 1.5;
        int Kpe;
        if(loco) Kpe = 0;
        else Kpe = 12;
        double alok = asu + Kpe;
        return alok;
    }

    /*
    Переменная tunnel должна быть установлена в true, если нужны значения для тоннелей; задается пользователем.
     */
    protected double calcAn(LineType lt, boolean tunnel){
        if(tunnel) return lt.getaNTunnel();
        else return lt.getaN();
    }
}
