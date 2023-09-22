package br.caioa.electriccar.data.local

import android.content.ContentValues
import android.content.Context
import android.provider.BaseColumns
import android.util.Log
import br.caioa.electriccar.data.local.CarsContract.CarEntry.COLUMN_NAME_BATERIA
import br.caioa.electriccar.data.local.CarsContract.CarEntry.COLUMN_NAME_CAR_ID
import br.caioa.electriccar.data.local.CarsContract.CarEntry.COLUMN_NAME_POTENCIA
import br.caioa.electriccar.data.local.CarsContract.CarEntry.COLUMN_NAME_PRECO
import br.caioa.electriccar.data.local.CarsContract.CarEntry.COLUMN_NAME_RECARGA
import br.caioa.electriccar.data.local.CarsContract.CarEntry.COLUMN_NAME_URL_PHOTO
import br.caioa.electriccar.domain.Car
import java.lang.Exception

class CarRepository(private val context: Context) {
    fun save(car: Car): Boolean {
        var isSaved = false
        try {
            val dbHelper = CarsDbHelper(context)
            val db = dbHelper.writableDatabase
            val values = ContentValues().apply {
                put(COLUMN_NAME_CAR_ID, car.id)
                put(COLUMN_NAME_PRECO, car.preco)
                put(COLUMN_NAME_BATERIA, car.bateria)
                put(COLUMN_NAME_POTENCIA, car.potencia)
                put(COLUMN_NAME_RECARGA, car.recarga)
                put(COLUMN_NAME_URL_PHOTO, car.urlPhoto)
            }

            val inserted = db?.insert(CarsContract.CarEntry.TABLE_NAME, null, values)
            if (inserted != null) {
                isSaved = true
            }
        } catch(ex: Exception) {
            ex.message?.let {
                Log.e("Erro ao inserir ->", it)
            }
        }

        return isSaved
    }

    fun findCarById(id: Int) : Car {
        val dbHelper = CarsDbHelper(context)
        val db = dbHelper.writableDatabase
        val columns = arrayOf(
            BaseColumns._ID,
            COLUMN_NAME_CAR_ID,
            COLUMN_NAME_PRECO,
            COLUMN_NAME_BATERIA,
            COLUMN_NAME_POTENCIA,
            COLUMN_NAME_RECARGA,
            COLUMN_NAME_URL_PHOTO
        )

        val filter = "$COLUMN_NAME_CAR_ID = ?"
        val filterValues = arrayOf(id.toString())

        val cursor = db.query(
            CarsContract.CarEntry.TABLE_NAME,
            columns,
            filter,
            filterValues,
            null,
            null,
            null
        )

        var itemId: Long = 0
        var preco = ""
        var bateria = ""
        var potencia = ""
        var recarga = ""
        var urlPhoto = ""

        with(cursor) {
            while (moveToNext()) {
                itemId = getLong(getColumnIndexOrThrow(COLUMN_NAME_CAR_ID))
                Log.d("ID -> ", itemId.toString())

                preco = getString(getColumnIndexOrThrow(COLUMN_NAME_PRECO))
                Log.d("Preco -> ", preco)

                bateria = getString(getColumnIndexOrThrow(COLUMN_NAME_BATERIA))
                Log.d("Preco -> ", bateria)

                potencia = getString(getColumnIndexOrThrow(COLUMN_NAME_POTENCIA))
                Log.d("Preco -> ", potencia)

                recarga = getString(getColumnIndexOrThrow(COLUMN_NAME_RECARGA))
                Log.d("Preco -> ", recarga)

                urlPhoto = getString(getColumnIndexOrThrow(COLUMN_NAME_URL_PHOTO))
                Log.d("Preco -> ", urlPhoto)
            }
        }
        cursor.close()
        return Car(
            id = itemId.toInt(),
            preco = preco,
            bateria = bateria,
            potencia = potencia,
            recarga = recarga,
            urlPhoto = urlPhoto,
            isFavorite = true
        )
    }

    fun saveIfNotExist(car: Car) {
        val foundCar = findCarById(car.id)

        if (foundCar.id == ID_WHEN_NO_CAR) {
            save(car)
        }
    }

    fun getAll() : List<Car> {
        val dbHelper = CarsDbHelper(context)
        val db = dbHelper.writableDatabase
        val columns = arrayOf(
            BaseColumns._ID,
            COLUMN_NAME_CAR_ID,
            COLUMN_NAME_PRECO,
            COLUMN_NAME_BATERIA,
            COLUMN_NAME_POTENCIA,
            COLUMN_NAME_RECARGA,
            COLUMN_NAME_URL_PHOTO
        )

        val cursor = db.query(
            CarsContract.CarEntry.TABLE_NAME,
            columns,
            null,
            null,
            null,
            null,
            null
        )

        val cars = mutableListOf<Car>()

        with(cursor) {
            while (moveToNext()) {
                val itemId = getLong(getColumnIndexOrThrow(COLUMN_NAME_CAR_ID))
                Log.d("ID -> ", itemId.toString())

                val preco = getString(getColumnIndexOrThrow(COLUMN_NAME_PRECO))
                Log.d("Preco -> ", preco)

                val bateria = getString(getColumnIndexOrThrow(COLUMN_NAME_BATERIA))
                Log.d("Preco -> ", bateria)

                val potencia = getString(getColumnIndexOrThrow(COLUMN_NAME_POTENCIA))
                Log.d("Preco -> ", potencia)

                val recarga = getString(getColumnIndexOrThrow(COLUMN_NAME_RECARGA))
                Log.d("Preco -> ", recarga)

                val urlPhoto = getString(getColumnIndexOrThrow(COLUMN_NAME_URL_PHOTO))
                Log.d("Preco -> ", urlPhoto)

                cars.add(
                    Car(
                        id = itemId.toInt(),
                        preco = preco,
                        bateria = bateria,
                        potencia = potencia,
                        recarga = recarga,
                        urlPhoto = urlPhoto,
                        isFavorite = true
                    )
                )
            }
        }
        cursor.close()
        return cars
    }

    companion object {
        const val ID_WHEN_NO_CAR = 0
    }

}