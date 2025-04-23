module ca.ucalgary.ahnaf.farhankhan.groupprojectgui1 {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.desktop;
    requires junit;
    requires org.junit.jupiter.api;


    opens ca.ucalgary.ahnaf.farhankhan.groupprojectgui1 to javafx.fxml;
    exports ca.ucalgary.ahnaf.farhankhan.groupprojectgui1;
    exports ca.ucalgary.ahnaf.farhankhan.groupprojectgui1.objects;
    exports ca.ucalgary.ahnaf.farhankhan.groupprojectgui1.util;
}
