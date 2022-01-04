package sainero.dani.intermodular.navigation

sealed class Destinations (
    val route: String
){
    object  MainAdministrationActivity: Destinations("Administration.MainAdministrationActivity")
    object  MainActivity: Destinations("MainActivity")
    object  Login: Destinations("Login")
    object  AccessToTables: Destinations("Cobrador.AccessToTables")
    object  Employeeanager: Destinations("Administration.EmployeeManager")
    object  ProductTypeAdministration: Destinations("Administration.ProductTypeAdministration")
    object  ZoneAdministration: Destinations("Administration.ZoneAdministration")
    object  CreateOrder  : Destinations("Cobrador.CreateOrder")
}