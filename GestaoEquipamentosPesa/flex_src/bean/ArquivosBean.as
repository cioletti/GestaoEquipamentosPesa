package bean
{
	[RemoteClass(alias="com.gestaoequipamentos.bean.ArquivosBean")]
	public class ArquivosBean
	{	
		
			private var _id: Number;
			private var _descricao: String;
			
			public function ArquivosBean(){}
			
			public function get id(): Number{return _id};
			public function set id(id: Number): void{this._id = id}; 
			
			public function get descricao(): String{return _descricao};
			public function set descricao(descricao: String): void{this._descricao = descricao}; 
		}
	}
