package com.vas.tmkmanager.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import com.vas.tmkmanager.database.dao.*
import com.vas.tmkmanager.database.entities.*
import java.util.concurrent.Executors


@Database(
    entities = [Client::class, CorporateBody::class, Installer::class, Item::class,
        ItemStoreroom::class, Manager::class, OrderTMK::class, PhysicalPerson::class,
        Selling::class, Staffer::class, Storekeeper::class, Storeroom::class],
    version = 1)
 abstract class TmkDatabase : RoomDatabase() {

    abstract fun getClientDao(): ClientDao
    abstract fun getCorporateBodyDao(): CorporateBodyDao
    abstract fun getInstallerDao(): InstallerDao
    abstract fun getItemDao(): ItemDao
    abstract fun getItemStoreroomDao(): ItemStoreroomDao
    abstract fun getManagerDao(): ManagerDao
    abstract fun getOrderTmkDao(): OrderTmkDao
    abstract fun getPhysicalPersonDao(): PhysicalPersonDao
    abstract fun getSellingDao(): SellingDao
    abstract fun getStafferDao(): StafferDao
    abstract fun getStorekeeperDao(): StorekeeperDao
    abstract fun getStoreroomDao(): StoreroomDao

    companion object {
        @Volatile private var INSTANCE: TmkDatabase? = null

        fun getInstance(context: Context): TmkDatabase =
            INSTANCE ?: synchronized(this) {
                INSTANCE ?: buildDatabase(context).also { INSTANCE = it }
            }

        private fun buildDatabase(context: Context) =
            Room.databaseBuilder(context.applicationContext,
                TmkDatabase::class.java, "Tmk.db")
                // prepopulate the database after onCreate was called
                .addCallback(object : Callback() {
                    override fun onCreate(db: SupportSQLiteDatabase) {
                        super.onCreate(db)
                        // insert the data on the IO Thread
                        ioThread {
                            getInstance(context).getClientDao().insert(PREPOPULATE_CLIENT)
                            getInstance(context).getCorporateBodyDao().insert(PREPOPULATE_CORPORATE)
                            getInstance(context).getPhysicalPersonDao().insert(PREPOPULATE_PHYSICAL)
                            getInstance(context).getItemDao().insert(PREPOPULATE_ITEM)
                            getInstance(context).getOrderTmkDao().insert(PREPOPULATE_ORDER)
                            getInstance(context).getStoreroomDao().insert(PREPOPULATE_STOREROOM)
                            getInstance(context).getItemStoreroomDao().insert(
                                PREPOPULATE_ITEM_STOREROOM
                            )
                            getInstance(context).getStafferDao().insert(PREPOPULATE_STAFFER)
                            getInstance(context).getInstallerDao().insert(PREPOPULATE_INSTALLER)
                            getInstance(context).getStorekeeperDao().insert(PREPOPULATE_STOREKEEPER)
                            getInstance(context).getManagerDao().insert(PREPOPULATE_MANAGER)
                            getInstance(context).getSellingDao().insert(PREPOPULATE_SELLING)
                        }
                    }
                })
                .build()

        val PREPOPULATE_CLIENT = listOf(
            Client("ООО \"Восход\"", "89951350468", "город Москва, Ленинградский проспект, дом 73А, строение 3", "Юр"),
            Client("Григорий", "89105461298", "город Москва, улица Вострухина, дом 5А", "Физ"),
            Client("Михаил","89951350754", "город Москва, улица Космонавта Волкова, дом 10, строение 1", "Физ"),
            Client("Анна","89951350897", "город Москва, Балтийская улица, дом 8, строение 15", "Физ"),
            Client("ИП \"Окна\"","89951351040", "город Москва, Климентовский переулок, дом 1, строение 3", "Юр"),
            Client("ЗАО ТМК","89951351183", "город Москва, улица Пречистенка, дом 19/11, строение 2", "Юр"),
            Client("Анастасия","89951351326", "город Москва, Старая Басманная улица, дом 16/1", "Физ"),
            Client("Владимир","89951351469", "город Москва, город Зеленоград, корпус 1212", "Физ"),
            Client("Александр","89951351612", "город Москва, Гурьевский проезд, дом 25, корпус 1", "Физ"),
            Client("Владимир","89951351755", "город Москва, 6-я Радиальная улица, дом 17", "Физ")
        )
        val PREPOPULATE_CORPORATE = listOf(
            CorporateBody(1, "40802 810 4 6442 0129813"),
            CorporateBody(5, "40802 810 4 6442 0129813"),
            CorporateBody(6, "40802 810 4 6424 0129813")
        )
        val PREPOPULATE_PHYSICAL = listOf(
            PhysicalPerson(2, "2814380533"),
            PhysicalPerson(3, "4410123536"),
            PhysicalPerson(4, "4356345843"),
            PhysicalPerson(7, "1238345887"),
            PhysicalPerson(8, "4545674143"),
            PhysicalPerson(9, "5467432543"),
            PhysicalPerson(10, "3445499876"),
        )
        val PREPOPULATE_ITEM = listOf(
            Item("БП 1-30", "входные двери", 14960.toDouble()),
            Item("АКП 60", "входные двери", 15070.toDouble()),
            Item("Smart 21", "входные двери", 8900.toDouble()),
            Item("Smart 23", "входные двери", 8500.toDouble()),
            Item("Smart 24", "входные двери", 9000.toDouble()),
            Item("Модель 42", "входные двери", 12000.toDouble()),
            Item("Clix Floor Plus", "ламинат", 899.toDouble()),
            Item("Belfast 1-006S", "линолеум", 699.toDouble()),
            Item("Smart 27", "межкомнатные двери", 4400.toDouble()),
            Item("Smart 28", "межкомнатные двери", 4500.toDouble())
        )
        val PREPOPULATE_ORDER = listOf(
            OrderTMK(1, 1, 1,
                "14.04.2021", "15.04.2021", "20.04.2021",
                "завершен", 14960.toDouble(), 1),
            OrderTMK(2, 1, 7,
                "14.04.2021", "15.04.2021", "20.04.2021",
                "завершен", 44950.toDouble(), 50),
            OrderTMK(3, 3, 5,
                "16.04.2021", "17.04.2021", "22.04.2021",
                "завершен", 9000.toDouble(), 1),
            OrderTMK(4, 4, 9,
                "17.04.2021", "18.04.2021", "23.04.2021",
                "завершен", 13200.toDouble(), 3),
            OrderTMK(5, 5, 6,
                "18.04.2021", "19.04.2021", "24.04.2021",
                "завершен", 12000.toDouble(), 1),
            OrderTMK(6, 6, 8,
                "19.04.2021", "20.04.2021", "25.04.2021",
                "завершен", 27960.toDouble(), 40),
            OrderTMK(7, 6, 10,
                "19.04.2021", "20.04.2021", "25.04.2021",
                "завершен", 4500.toDouble(), 1),
            OrderTMK(8, 7, 4,
                "21.04.2021", "22.04.2021", "27.04.2021",
                "завершен", 8500.toDouble(), 1),
            OrderTMK(9, 8, 3,
                "22.04.2021", "23.04.2021", "28.04.2021",
                "завершен", 8900.toDouble(), 1),
            OrderTMK(10, 10, 2,
                "23.04.2021", "24.04.2021", "29.04.2021",
                "завершен", 15070.toDouble(), 2),
        )
        val PREPOPULATE_STOREROOM = listOf(
            Storeroom("г. Балашиха, ул. Чехова, д. 3", "с 10:00 до 15:00"),
            Storeroom("пос. Сосенское, ул. Александры Монаховой, дом 109, корпус 3", "с 10:00 до 15:00"),
            Storeroom("г. Москва, ул. Владимирская 1-я, д. 31", "с 10:00 до 15:00")
        )
        val PREPOPULATE_ITEM_STOREROOM = listOf(
            ItemStoreroom(1, 1, 20),
            ItemStoreroom(2, 1, 20),
            ItemStoreroom(3, 1, 15),
            ItemStoreroom(4, 2, 30),
            ItemStoreroom(5, 2, 25),
            ItemStoreroom(6, 1, 40),
            ItemStoreroom(7, 2, 1000),
            ItemStoreroom(8, 3, 2000),
            ItemStoreroom(9, 3, 50),
            ItemStoreroom(9, 1, 60),
            ItemStoreroom(10, 2, 40),
            ItemStoreroom(10, 3, 80)
        )
        val PREPOPULATE_STAFFER = listOf(
            Staffer("Иванов А. В.", "89107453423", "ivanov2000@gmail.com", "Кладовщик"),
            Staffer("Петров М. И.", "89641325645", "petrov9@gmail.com", "Монтажник"),
            Staffer("Сеткина А. С.", "89641325646", "anna_set@gmail.com", "Менеджер"),
            Staffer("Васильева А. В.", "89641325647", "vasilev1972@gmail.com", "Менеджер"),
            Staffer("Гордеев Р. П.", "89641325648", "tmk@gmail.com", "Кладовщик"),
            Staffer("Кузнецов Д. С.", "89641325649", "danil228@gmail.com", "Кладовщик"),
            Staffer("Воронцов П. А.", "89641325650", "voron1964@gmail.com", "Монтажник"),
            Staffer("a", "89641325650", "voron1964@gmail.com", "Менеджер") //8
        )
        val PREPOPULATE_INSTALLER = listOf(
            Installer(2, "Тёплый Стан"),
            Installer(7, "Отрадное")
        )
        val PREPOPULATE_STOREKEEPER = listOf(
            Storekeeper(1, 1),
            Storekeeper(5, 2),
            Storekeeper(6, 3)
        )
        val PREPOPULATE_MANAGER = listOf(
            Manager(3, 1),
            Manager(4, 2)
        )
        val PREPOPULATE_SELLING = listOf(
            Selling(1, 1, 3, "20.04.2021", 14960.toDouble()),
            Selling(2, 7, 3, "20.04.2021", 44950.toDouble()),
            Selling(3, 5, 3, "22.04.2021", 9000.toDouble()),
            Selling(4, 9, 3, "23.04.2021", 13200.toDouble()),
            Selling(5, 6, 3, "24.04.2021", 12000.toDouble()),
            Selling(6, 8, 4, "25.04.2021", 27960.toDouble()),
            Selling(7, 10, 4, "25.04.2021", 4500.toDouble()),
            Selling(8, 4, 4, "27.04.2021", 8500.toDouble()),
            Selling(9, 3, 4, "28.04.2021", 8900.toDouble()),
            Selling(10, 2, 4, "29.04.2021", 15070.toDouble())
        )


        private val IO_EXECUTOR = Executors.newSingleThreadExecutor()
        /**
         * Utility method to run blocks on a dedicated background thread, used for io/database work.
         */
        fun ioThread(f : () -> Unit) {
            IO_EXECUTOR.execute(f)
        }
    }
}