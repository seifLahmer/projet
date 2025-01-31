package Test;

import Entite.Equipment;
import Services.ServiceEquipement;

import java.sql.SQLException;
import java.util.Date;
import java.util.List;
import java.util.Map;

public class Test {
    public static void main(String[] args) throws SQLException {
        ServiceEquipement service = new ServiceEquipement();

        // 1. Adding a new equipment
        Equipment newEquipment = new Equipment(0, "Laptop", "Electronics", 10, new Date(), new Date(), "Good");
        service.ajouter(newEquipment);  // This will print "Equipment added successfully!"

        // Fetch the equipment ID after insertion
        List<Equipment> allEquipment = service.getAll();
        int lastInsertedId = allEquipment.get(allEquipment.size() - 1).getEquipementID();
        System.out.println("Last Inserted Equipment ID: " + lastInsertedId);

        // Set the ID of newEquipment to the actual ID assigned by the database
        newEquipment.setEquipementID(lastInsertedId);

        // 2. Fetching all equipment and printing it
        System.out.println("All Equipment:");
        for (Equipment e : allEquipment) {
            System.out.println(e);  // This will print each equipment
        }

        // 3. Fetching equipment by ID and printing it
        System.out.println("\nFetching Equipment by ID:");
        Equipment equipmentById = service.getById(lastInsertedId);  // Use the real ID
        if (equipmentById != null) {
            System.out.println(equipmentById);  // This will print the equipment if found
        } else {
            System.out.println("No equipment found with ID: " + lastInsertedId);
        }

        // 4. Updating the equipment (Change name and quantity)
        System.out.println("\nUpdating Equipment:");
        Map<String, Object> updateData = Map.of(
                "equipementName", "Updated Laptop",
                "quantity", 5
        );
        service.update(newEquipment, updateData);  // This will print "Equipment updated successfully!"

        // Fetching updated equipment by ID
        Equipment updatedEquipment = service.getById(lastInsertedId);
        System.out.println("Updated Equipment: " + updatedEquipment);  // This should show updated values

        // 5. Deleting the equipment
        System.out.println("\nDeleting Equipment:");
        service.supprimer(newEquipment);  // This will print "Equipment deleted successfully!"

        // Fetching all equipment after deletion
        System.out.println("\nAll Equipment after deletion:");
        List<Equipment> equipmentAfterDelete = service.getAll();
        for (Equipment e : equipmentAfterDelete) {
            System.out.println(e);  // This should print remaining equipment (without the deleted one)
        }
    }
}
