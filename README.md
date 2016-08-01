# Conversion Tracking

una infraestructura de rastreo de trafego anónimo.

## TX specs

áreas clasifican sitios:

    // post Sitio (debe ser único)
    {
		título:
		número: (número_código_áreal)
	}
	
	// post Visitante Anónimo 
	{
		número:
	}
	/* añadir a un total de visitantes anónimo */
	
	// post Ingresar
	{
		nombre:
		contraseña:
		número: (número_código_áreal)
	}
	/* reduce uno de total del área */
	
	// post Registrar
	{
		nombre:
		contraseña:
		número: (número_código_áreal)	
	}
	
nota: si una transacción usuarial usa un número desafijado a un área, déjalo ser.
	
## puntas RESTful prescrito

### POST

* nuevo sitio
* ingresar
* añadir visitante anónimo por área
* nuevo cuenta

### GET

* total por un área
* los sitios
* cuentas por un sitio
* rato de conversión por un sitio