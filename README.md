# CursoAtapp

Para acceder a la operación de algoritmia:
  -Url:/api/doctor/stats?fIni=02-02-2018&fFin=30-06-2018
  -Debe introducirse la fecha inicial y la final con el formato dd-mm-yyyy
  -El doctor en la base de datos de ésta aplicación tiene un atributo llamado externalApiId, el valor de éste atributo debe ser igual
    a algún id de los doctores de la aplicación externa de la cual consume el servicio.
    
Para acceder a la operación de query compleja:
  -Url:/api/doctor/popularDoctors/{max}
  -{max} será un valor para limitar el top, ejemplo si {max} vale 5 se devolverá el top 5 de doctores con más pacientes.
