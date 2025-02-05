package assignment17;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.util.Scanner;
import org.json.JSONObject;

class DBConnection{
	private static Connection con;
	public static Connection getConnection() {
		if(con == null) {
			try {
				con = DriverManager.getConnection("jdbc:postgresql://localhost:5433/empdb","postgres","tiger");
			}catch(Exception e) {
				e.printStackTrace();
			}
		}
		return con;
	}
	public static void closeConnection() {
		con=null;
	}
}
class DBOperation {
   
    public void insert() {
        try {
        	Connection con = DBConnection.getConnection(); 
            PreparedStatement pstmt = con.prepareStatement("INSERT INTO responses (request,response) VALUES (?, ?::jsonb)");
            pstmt.setTimestamp(1, new java.sql.Timestamp(System.currentTimeMillis()));

           
            JSONObject obj = new JSONObject();
            obj.put("response_time", "2025-02-04T14:00:00");  
            obj.put("method", "GET");  
            obj.put("status_code", "200");

            
            pstmt.setString(2, obj.toString());
            
            pstmt.executeUpdate();
            System.out.println("Data inserted successfully!");
            
        } catch (Exception e) {
            e.printStackTrace(); 
        }
    }

    
    public void view() {
        try {
        	Connection con = DBConnection.getConnection(); 
            PreparedStatement pstmt = con.prepareStatement("SELECT * FROM responses");
            ResultSet rs = pstmt.executeQuery() ;
            ResultSetMetaData rsmd = rs.getMetaData();
            int columns = rsmd.getColumnCount();
            
            if (!rs.next()) {
                System.out.println("No data found.");
                return;
            }
            
            do {
                for (int i = 1; i <= columns; i++) {
                    System.out.println(rsmd.getColumnName(i) + " : " + rs.getString(i));
                }
                System.out.println();
            } while (rs.next());
            
        } catch (Exception e) {
            e.printStackTrace();  
        }
    }
    public void delete() {
    	try {
    	Connection con = DBConnection.getConnection(); 
    	System.out.println("Enter reposne type to delete:");
    	String res = new Scanner(System.in).nextLine();
    	PreparedStatement pstmt = con.prepareStatement("delete from responses where response->>'method'= ?");
    	pstmt.setString(1,res);
    	pstmt.execute();
    	}catch(Exception e) {
    		System.out.println("Deleted Sucessfully");
    	}
    }
    public void update() {
    	try {
    	Connection con = DBConnection.getConnection(); 
    	System.out.println("Enter reponse type to update:");
    	String res = new Scanner(System.in).nextLine();
    	System.out.println("Enter reponse val to update:");
    	String resval = new Scanner(System.in).nextLine();
    	String query = "UPDATE responses SET response = jsonb_set(response, '{method}', ?::jsonb) WHERE response->>'method' = ?";
    	PreparedStatement pstmt = con.prepareStatement(query);
    	pstmt.setString(1,"\"" + resval + "\"");
    	pstmt.setString(2,res);
    	pstmt.execute();
    	}catch(Exception e) {
    		System.out.println("updated Sucessfully");
    	}
    }
}

public class JsonConnection {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int choice = 0;
        
        do {
            System.out.println("1. Insert");
            System.out.println("2. View");
            System.out.println("3. Update");
            System.out.println("4. Delete");
            System.out.print("Enter Choice: ");
            choice = sc.nextInt();
            
            DBOperation op = new DBOperation();
            
            switch (choice) {
                case 1:
                    op.insert();
                    break;
                case 2:
                    op.view();
                    break;
                case 3:
                	op.update();
                    break;
                case 4:
                	op.delete();
                    break;
                case 5:
                    System.out.println("Exiting...");
                    break;
                default:
                    System.out.println("Invalid choice, try again.");
            }
        } while (choice != 5);
        
        sc.close();  
    }
}
