package br.caioa.electriccar.data

import br.caioa.electriccar.domain.Car

object CarFactory {

    val list = listOf<Car>(
        Car(
            id = 1,
            price = "R$ 300,000.00",
            battery = "300 kWh",
            power = "300 hp",
            charge = "30 min",
            urlPhoto = "www.google.com.br"
        ),        Car(
            id = 1,
            price = "R$ 200,000.00",
            battery = "250 kWh",
            power = "400 hp",
            charge = "45 min",
            urlPhoto = "www.google.com.br"
        ),
    )
}