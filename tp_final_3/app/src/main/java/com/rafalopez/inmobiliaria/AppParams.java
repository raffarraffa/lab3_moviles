package com.rafalopez.inmobiliaria;


/**
 *  parametros globales de la app
 */
public class AppParams {

        /** clave para acceder a las preferencias almacenadas de la app */
        public static final String PREFERENCES_DATA = "dataInmobiliaria";

        /** clave para almacenar y recuperar el token de app */
        public static final String TOKEN_KEY = "token";

        /** clave para almacenar y recuperar los inmuebles de propietario */
        public static final String INMUEBLE_KEY = "inmbuele";

        /** clave para almacenar y recuperar la info propietario. */
        public static final String PROPIETARIO_KEY = "propietario";

        /** URL base de la API para el entorno de desarrollo local */
        public static final String URL_BASE = "http://192.168.10.25:8104/api/";

        /** URL base de la API para el entorno de desarrollo produccion */
        // public static final String URL_BASE = "https://api.rafalopez.ar/";
        /** LOs servidores APIy Archviso podrian ser diferentes se seprtn las URL base */
        /** URL base para acceder a los archivos almacenados en el servidor local*/
        public static final String URL_BASE_FILE = "http://192.168.10.25:8104/archivos/";

        /** URL base para acceder a los archivos almacenados en el servidor produccion*/
        // public static final String URL_BASE_FILE = "https://api.rafalopez.ar/archivos/";
        /** Utils Maps inmobilairia */
        public  static  double MAP_LAT= -33.1866142;
        public  static  double MAP_LON= -66.3114745;
        public  static  float MAP_ZOOM= 10.0f;
        public  static  String  MAP_TITLE= "API";
}
