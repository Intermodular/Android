package sainero.dani.intermodular.Navigation

sealed class Destinations (
    val route: String
){
    object  MainAdministrationActivity: Destinations("Administration.MainAdministrationActivity")
    object  MainActivity: Destinations("MainActivity")
    object  Login: Destinations("Login")
    object  AccessToTables: Destinations("Cobrador.AccessToTables")
    object  EmployeeManager: Destinations("Administration.Employee.EmployeeManager")
    object  ProductTypeManager: Destinations("Administration.Products.Types.ProductTypeManager")
    object  ZoneManager: Destinations("Administration.ZoneManager")
    object  CreateOrder: Destinations("Cobrador.CreateOrder")
    object  EditEmployee: Destinations("Administration.Employee.EditEmployee")
    object  ProductManager: Destinations("Administration.Products.ProductManager")
    object  EditProduct:  Destinations("Administration.Products.EditProduct")
    object  NewProduct: Destinations("Administration.Products.NewProduct")
    object  NewEmployee: Destinations("Administration.Employee.NewEmployee")
    object  Ingredient: Destinations("Administration.Products.Ingredients.Ingredients")
    object  EditZone: Destinations("Administration.Zone.EditZone")
    object  NewZone: Destinations("Administration.Zone.NewZone")
    object  ProductEditType: Destinations("Administration.Products.Types.ProductEditType")
    object  Especifications: Destinations("Administration.Products.Especifications.Especifications")
    object  ProductNewType: Destinations("Administration.Products.Types.ProductNewType")
    object  TableManager: Destinations("Administration.Zone.TableManager")
    object  EditTable: Destinations("Administration.Zone.EditTable")
    object  NewTable: Destinations("Administration.Zone.NewTable")
}