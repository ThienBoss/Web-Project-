package dao;

import java.io.Closeable;
import java.io.IOException;
import java.sql.*;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;
import database.DatabaseConnection;
import database.Query;
import database.TablesName;
import model.Streamer;
import database.DatabaseConnection;

public class StreamerDao implements DAO<Streamer>, Closeable {
    private DatabaseConnection databaseConnection;
    private ResultSet rs ;
    private java.sql.Statement st ;
    String[] listOfFields = {
            TablesName.getStreamerInfo(),
            TablesName.getStreamerImage(),
            TablesName.getStreamerHirePrice(),
            TablesName.getStreamerStar(),
            TablesName.getStreamerStatus(),
            TablesName.getStreamerLocation()};

        
    
    public StreamerDao() throws SQLException {
        databaseConnection = new DatabaseConnection();
        st = databaseConnection.connectToDatabase().createStatement();
    }
	

	@Override
	public void save(Streamer s) throws SQLException {

        int streamerID = 0;
        String[] fields = {TablesName.getStreamerID()};
        String[] id = {TablesName.getStreamerImage(),TablesName.getStreamerLocation()};
        String[] conditions = {s.getImages(),s.getLocation()};
        String streamerEmail =  "" ;
        String[] streamerDetailInfo = {
           s.getInformation(),
           s.getImages(),
           Integer.toString(s.getHirePrice()),
           Integer.toString(s.getStar()),
           Integer.toString(s.getStatus()),
           s.getLocation()
        };

        st.executeUpdate(Query.insertToFields(TablesName.getStreamerTable(),listOfFields, streamerDetailInfo));          

        try {
           rs = st.executeQuery(Query.selectHasCondition(
                       fields,
                       TablesName.getStreamerTable(),
                       id, conditions ));
           System.out.println("Query select has condition : " + Query.selectHasCondition(
                       fields,
                       TablesName.getStreamerTable(),
                       id, conditions ));
           while(rs.next()) {
               streamerID = rs.getInt(1);
           }
           s.setStreamerId (streamerID);
           System.out.println("StreamID : " + s.getStreamerId());
           
        } catch (SQLException e) {
        	e.printStackTrace();
        }
		String[] streamerAccount = { 
				s.getUserName(),
				s.getPassword(),
				s.getFullName(),
				s.getEmail(),
				Integer.toString(s.getAge()),
				Integer.toString(s.getDonated()), s.getSex(),
				Integer.toString(s.getStreamerId()) };

        st.executeUpdate(Query.insertToTable(TablesName.getUserTable(), streamerAccount));
	}

	@Override
	public void update(Streamer s) throws SQLException {
        String streamerEmail =  "" ;
        String[] streamerDetailInfo = {
           s.getInformation(),
           s.getImages(),
           Integer.toString(s.getHirePrice()),
           Integer.toString(s.getStar()),
           Integer.toString(s.getStatus()),
           s.getLocation()
        };
        String[] streamerId = {TablesName.getStreamerID()};
        String[] streamerConditions = { Integer.toString(s.getStreamerId())};
	    st.executeUpdate(Query.updateTable(TablesName.getStreamerTable(),listOfFields,streamerDetailInfo,streamerId,streamerConditions));
	}

	@Override
	public void delete(Streamer s) throws SQLException {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void close() throws IOException {
		
	}

}
