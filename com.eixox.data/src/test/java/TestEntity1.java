import java.util.Date;

import com.eixox.data.Column;
import com.eixox.data.ColumnType;
import com.eixox.data.Table;

@Table
public class TestEntity1 {

	@Column(type = ColumnType.Identity)
	public int id;

	@Column
	public String name;

	@Column
	public String email;

	@Column
	public Date birthDay;

	@Column
	public long cpf;

	@Column
	public Date dateCreated;

	@Column
	public Date dateUpdated;

}
