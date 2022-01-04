package sainero.dani.intermodular.navigation

sealed class Destinations (
    val route: String
){
    object  MainAdministrationActivity: Destinations("MainAdministrationActivity")
    object  MainActivity: Destinations("MainActivity")
    object  Login: Destinations("Login")
    object  AccessToTables: Destinations("AccessToTables")
    object  Employeeanager: Destinations("EmployeeManager")
    object  ProductTypeAdministration: Destinations("ProductTypeAdministration")
    object  ZoneAdministration: Destinations("ZoneAdministration")
    object  CreateOrder  : Destinations("CreateOrder")
}