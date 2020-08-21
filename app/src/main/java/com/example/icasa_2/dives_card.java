package com.example.icasa_2;

public class dives_card {
    private  int id;
    private  String nocontrato;
    private  String consumo;
    private  String direccion;
    private  String colonia;
    private  String municipio;
    private  String fecha_corte;
    private  String fecha_instalacion;
    private  String mesEnero,mesFebrero,mesMarzo,mesAbril,mesMayo,mesJunio,mesJulio,mesAgosto,mesSeptiembre,mesOctubre,mesNoviembre,mesDiciembre;





    public dives_card() {

        //empty constructor needed
    }

    //Representa el objeto que se traerá de base de datos firebase

    public dives_card (int id1,String nocontrato1,String consumo1,String direccion1,String colonia1,String municipio1,
                       String fecha_corte1, String fecha_instalacion1, String mesEnero,
                       String mesFebrero,String mesMarzo, String mesAbril, String mesMayo, String mesJunio, String mesJulio,
                       String mesAgosto, String mesSeptiembre, String mesOctubre, String mesNoviembre, String mesDiciembre){


        this.id = id1;
        this.nocontrato = nocontrato1;
        this.consumo = consumo1;
        this.direccion = direccion1;
        this.colonia = colonia1;
        this.municipio = municipio1;
        this.fecha_corte = fecha_corte1;
        this.fecha_instalacion = fecha_instalacion1;
        this.mesEnero = mesEnero;
        this.mesFebrero = mesFebrero;
        this.mesMarzo = mesMarzo;
        this.mesAbril = mesAbril;
        this.mesMayo = mesMayo;
        this.mesJunio = mesJunio;
        this.mesJulio = mesJulio;
        this.mesAgosto = mesAgosto;
        this.mesSeptiembre = mesSeptiembre;
        this.mesOctubre = mesOctubre;
        this.mesNoviembre = mesNoviembre;
        this.mesDiciembre = mesDiciembre;


    }


    public int getid() {
        return id;
    }

    public String getnocontrato() {
        return nocontrato;
    }

    public String getconsumo() {
        return consumo;
    }

    public String getdireccion() {
        return direccion;
    }

    public String getcolonia() {
        return colonia;
    }

    public String getmunicipio() {
        return municipio;
    }

    public String getfecha_corte() {
        return fecha_corte;
    }

    public String getfecha_instalacion() {
        return fecha_instalacion;
    }


    public String getmesEnero(){ return  mesEnero;}
    public String getmesFebrero(){ return  mesFebrero;}
    public String getmesMarzo(){ return  mesMarzo;}
    public String getmesAbril(){ return  mesAbril;}
    public String getmesMayo(){ return  mesMayo;}
    public String getmesJunio(){ return  mesJunio;}
    public String getmesJulio(){ return  mesJulio;}
    public String getmesAgosto(){ return  mesAgosto;}
    public String getmesSeptiembre(){ return  mesSeptiembre;}
    public String getmesOctubre(){ return  mesOctubre;}
    public String getmesNoviembre(){ return  mesNoviembre;}
    public String getmesDiciembre(){ return  mesDiciembre;}




}
