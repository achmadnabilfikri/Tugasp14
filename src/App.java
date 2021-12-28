import java.util.InputMismatchException;
import java.util.Scanner;
import com.mysql.cj.protocol.Resultset;
import java.io.BufferedReader;
import java.io.FileReader; 
import java.sql.*;

public class App {
    static Connection conn;
    public static void main(String[] args) {
        Scanner terimaInput = new Scanner(System.in);
        String pilihanUser;
        Boolean isLanjutkan = true;

        String url= "jdbc:mysql://localhost:3306/games";
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            conn = DriverManager.getConnection(url, "root", "madarauchiha");
            System.out.println("class driver ditemukan");

            while (isLanjutkan) {
                System.out.println("\n-----------");
                System.out.println("Database ListGames");
                System.out.println("---------");
                System.out.println("1. Lihat data Games");
                System.out.println("2. Tambah Data Games");
                System.out.println("3. Ubah Data Games");
                System.out.println("4. Hapus data Games");
                System.out.println("5. Cari Data Games");

                System.out.println("\nMasukkan Pilihan Anda(1/2/3/4/5): ");
                pilihanUser = terimaInput.next();

                switch (pilihanUser) {
                    case "1":
                        lihatdata();
                        break;
                    
                    case "2":
                        tambahdata();
                        break;
                        
                    case "3":
                        ubahdata();
                        break;
                        
                    case "4":
                        hapusdata();
                        break;
                        
                    case "5":
                        caridata();
                        break;
                        
                    default:
                        System.err.println("input tidak dapat dikonfirmasi, silahkan masukkan nomor 1-5");

                }
                System.out.print("\nApakah Anda ingin melanjutkan [y/n]? ");
				pilihanUser = terimaInput.next();
				isLanjutkan = pilihanUser.equalsIgnoreCase("y");
            }
            System.out.println("\nTerima kasih");
        }
        catch(ClassNotFoundException ex) {
			System.err.println("Driver Error Ges");
			System.exit(0);
		}
        catch(SQLException e){
			System.err.println("Tidak berhasil koneksi");
		}
    }


private static void lihatdata() throws SQLException {
    String text1 = "\n===Daftar Seluruh Data Games===";
    System.out.println(text1.toUpperCase());
                    
    String sql ="SELECT * FROM pc";
    Statement statement = conn.createStatement();
    ResultSet result = statement.executeQuery(sql);
    
    while(result.next()){
        System.out.print("\nId\t: ");
        System.out.print(result.getInt("id"));
        System.out.print("\nName\t: ");
        System.out.print(result.getString("name"));
        System.out.print("\nGenre\t: ");
        System.out.print(result.getString("genre"));
        System.out.print("\nPrice\t: ");
        System.out.print(result.getString("price"));
        System.out.print("\nPlay Time\t: ");
        System.out.print(result.getString("play_time"));
        System.out.print("\nDeveloper\t: ");
        System.out.print(result.getString("developer"));
        System.out.print("\n");
    }
}

private static void tambahdata() throws SQLException{
    String text2 = "\n===Tambah Data Games===";
    System.out.println(text2.toUpperCase());
    
    Scanner terimaInput = new Scanner (System.in);
            
    try {
    
    System.out.print("Id\t: ");
    int id = terimaInput.nextInt();
    System.out.print("Name\t: ");
    String name = terimaInput.next();
    System.out.print("Genre\t: ");
    String genre = terimaInput.next();
    System.out.print("Price\t: ");
    String price = terimaInput.next();
    System.out.print("Play Time\t: ");
    String play_time = terimaInput.next();
    System.out.print("Developer\t: ");
    String developer = terimaInput.next();

    
    String sql = "INSERT INTO pc (id, name, genre, price, play_time, developer) VALUES ('"+id+"','"+name+"','"+genre+"','"+price+"','"+play_time+"','"+developer+"')";
                
    Statement statement = conn.createStatement();
    statement.execute(sql);
    System.out.println("Berhasil input data");

    } catch (SQLException e) {
        System.err.println("Terjadi kesalahan input data");
    } catch (InputMismatchException e) {
        System.err.println("Inputlah dengan angka saja");
       }
}

private static void ubahdata() throws SQLException{
    String text3 = "\n===Ubah Data Games===";
    System.out.println(text3.toUpperCase());
    
    Scanner terimaInput = new Scanner (System.in);
    
    try {
        lihatdata();
        System.out.print("Masukkan No. Id games yang akan di ubah atau update : ");
        Integer id = Integer.parseInt(terimaInput.nextLine());
        
        String sql = "SELECT * FROM pc WHERE id = " +id;
        
        Statement statement = conn.createStatement();
        ResultSet result = statement.executeQuery(sql);
        
        if(result.next()){
            
            System.out.print("Name ["+result.getString("name")+"]\t: ");
            String name = terimaInput.nextLine();
            
            System.out.print("Genre ["+result.getString("genre")+"]\t: ");
            String genre = terimaInput.nextLine();

            System.out.print("Price ["+result.getString("price")+"]\t: ");
            String price = terimaInput.nextLine();

            System.out.print("Play time ["+result.getString("play_time")+"]\t: ");
            String play_time = terimaInput.nextLine();

            System.out.print("Developer ["+result.getString("developer")+"]\t: ");
            String developer = terimaInput.nextLine();

            


               
            sql = "UPDATE pc SET name='"+name+"',genre= '"+genre+"','"+price+"'.'"+play_time+"','"+developer+"' WHERE id='"+id+"'";
            //System.out.println(sql);

            if(statement.executeUpdate(sql) > 0){
                System.out.println("Berhasil memperbaharui data mahasiswa (No. id "+id+")");
            }
        }
        statement.close();        
    } catch (SQLException e) {
        System.err.println("Terjadi kesalahan dalam mengedit data");
        System.err.println(e.getMessage());
    }
    }

    private static void hapusdata() {
		String text4 = "\n===Hapus Data Mahasiswa===";
		System.out.println(text4.toUpperCase());
		
		Scanner terimaInput = new Scanner (System.in);
		
		try{
	        lihatdata();
	        System.out.print("Ketik No. Id games yang ingin anda hapus : ");
	        Integer id= Integer.parseInt(terimaInput.nextLine());
	        
	        String sql = "DELETE FROM pc WHERE id = "+ id;
	        Statement statement = conn.createStatement();
	        //ResultSet result = statement.executeQuery(sql);
	        
	        if(statement.executeUpdate(sql) > 0){
	            System.out.println("Berhasil menghapus data games (No. id "+id+")");
	        }
	   }catch(SQLException e){
	        System.out.println("Terjadi kesalahan dalam menghapus data ");
	        }
		}
    
        
        private static void caridata () throws SQLException {
            String text5 = "\n===Cari Data Game===";
            System.out.println(text5.toUpperCase());
            
            Scanner terimaInput = new Scanner (System.in);
                    
            System.out.print("Masukkan Nama Game : ");
            
            String keyword = terimaInput.nextLine();
            Statement statement = conn.createStatement();
            String sql = "SELECT * FROM pc WHERE name LIKE '%"+keyword+"%'";
            ResultSet result = statement.executeQuery(sql); 
                    
            while(result.next()){
                System.out.print("\nNo. Id\t: ");
                System.out.print(result.getInt("id"));
                System.out.print("\nName\t: ");
                System.out.print(result.getString("name"));
                System.out.print("\nGenre\t: ");
                System.out.print(result.getString("genre"));
                System.out.print("\nPrice\t: ");
                System.out.print(result.getString("price"));
                System.out.print("\nPlay Time\t: ");
                System.out.print(result.getString("play_time"));
                System.out.print("\nDeveloper\t: ");
                System.out.print(result.getString("developer"));

                System.out.print("\n");
            }
        }    

}       


