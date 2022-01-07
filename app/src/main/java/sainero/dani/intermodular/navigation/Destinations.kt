package sainero.dani.intermodular.navigation

sealed class Destinations (
    val route: String
){
    object  MainAdministrationActivity: Destinations("Administration.MainAdministrationActivity")
    object  MainActivity: Destinations("MainActivity")
    object  Login: Destinations("Login")
    object  AccessToTables: Destinations("Cobrador.AccessToTables")
    object  EmployeeManager: Destinations("Administration.Employee.EmployeeManager")
    object  ProductTypeAdministration: Destinations("Administration.Products.ProductTypeAdministration")
    object  ZoneAdministration: Destinations("Administration.ZoneAdministration")
    object  CreateOrder: Destinations("Cobrador.CreateOrder")
    object  EditEmployee: Destinations("Administration.Employee.EditEmployee")
    object  CreateEmployee: Destinations("Administration.Employee.CreateEmployee")
    object  ProductManager: Destinations("Administration.Products.ProductManager")
    object  EditProduct:  Destinations("Administration.Products.EditProduct")
}