package distcalc;

import javax.json.Json;
import javax.json.JsonArray;
import javax.json.JsonArrayBuilder;
import javax.json.JsonObject;
import javax.ws.rs.*;
import javax.ws.rs.core.Response;

@Path("/prscalc")
public class DistanceCalculation {
     @GET
     @Path("/rmobile")
     @Produces("text/plain;charset=UTF-8")
     public Response getDistanceForMobileStation(
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
         return Response.status(200).entity(rMobile).build();
     }

     @GET
     @Path("/rstationary")
     @Produces("text/plain;charset=UTF-8")
     public Response getDistanceForStationaryStation(
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
         return Response.status(200).entity(rStationary).build();
     }
    @GET
    @Path("/intermediate")
    @Produces("text/plain;charset=UTF-8")
    public Response getIntermediateCalculationResults(
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
        return Response.status(200).entity(
                "Расчет дальности эффективной связи для линий: " + lineName +
                "<br><br>Исходные данные:" +
                "<br>Up моб. = " + uPMob + "\tUp стат. = " + uPStat +
                "<br>A пер. (A пер. в тоннеле) = " + aPer + "(" + aPerTunnel + ")" +
                "<br>An (An в тоннеле) = " + an + "(" + aNTunnel + ")" +
                "<br>Av = " + aV +
                "<br>Ku = " + Ku +
                "<br>Aon = " + aon +
                "<br>Atr = " + atr +
                "<br>Тоннель = " + tunnel +
                "<br>Скорость = " + speed +
                "<br>Расстояние между проводами линии = " + dist +
                "<br>Число нагрузок на линию = " + load +
                "<br>Число путей = " + lineQuantity +
                "<br>Длина фидера = " + feederLength +
                "<br>Возбуждение линии в месте анкеровки = " + anker +
                "<br>Тип перехода = " + crossType + "\tЧисло переходов = " + crossQuantity +
                "<br>Число трансформаторов = " + transfQuantity +
                "<br>Использование электровоза/тепловоза = " + loco +
                "<br><br>Результаты расчета:" +
                "<br>A прд. = " + Aprd +
                "<br>Umin моб. = " + uMinMobile + "\tUmin стат. = " + uMinStation +
                "<br>A пер. = " + Aper +
                "<br>A ст. = " + ast +
                "<br>A лин. = " + alin +
                "<br>A лок. = " + alok +
                "<br>An = " + an).build();
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
