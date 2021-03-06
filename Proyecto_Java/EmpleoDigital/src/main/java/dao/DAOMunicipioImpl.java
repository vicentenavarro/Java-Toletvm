package dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import javax.sql.DataSource;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;

import modelos.Municipio;

public class DAOMunicipioImpl implements DAOMunicipio {

	
	
	private class MunicipioRowMapper implements RowMapper<Municipio>{
		
		public Municipio mapRow(ResultSet rs, int numRow) throws SQLException{
			 
			return new Municipio(
					rs.getInt("id"),
					rs.getString("municipio"),
					rs.getInt("provinciaId"),
					rs.getString("slug"));
		}
	}
	
	DataSource dataSource;

	public DataSource getDataSource() {
		return dataSource;
	}

	public void setDataSource(DataSource dataSource) {
		this.dataSource = dataSource;
	}
	
	/**
	 * Modelo con el que se lee los datos en la BBDD con el identificador la provincia
	 */
	
	public Municipio read(int provinciaId){
		
		String sql="select id,municipio,slug from municipios where provincia_id";
		
		JdbcTemplate jdbc=new JdbcTemplate(dataSource);
		
		Municipio m=jdbc.queryForObject(sql, new Object[]{provinciaId},new MunicipioRowMapper());
		
		return m;
	}
	
	/**
	 * Modelo con que se crea un array para mostrar la lista de municipios por con orden id
	 */
	
	public List<Municipio> listar(){
		
		String sql="select id,municipio,slug,provincia_id from municipios order by municipio asc";
		
		JdbcTemplate jdbc=new JdbcTemplate(dataSource);
		
		List<Municipio> lista=jdbc.query(sql,new MunicipioRowMapper());
		
		return lista;
	}
	
	/**
	 * Modelo que crea un arraylist para mostra la listas por letras.
	 */
	public List<Municipio> listar(String letra){
		
		String sql="select id,muncipio,slug,provincia_id from municipios where slug like ?%"; 
		//REEVISAR ya que no se si ?% es para las palabras que empiezen- he vistos ejemplos que %? palabras que terminen y %?% que contenga
		JdbcTemplate jdbc=new JdbcTemplate(dataSource);
		
		List<Municipio> lista=jdbc.query(sql, new MunicipioRowMapper());
		
		return lista;
	}
}
