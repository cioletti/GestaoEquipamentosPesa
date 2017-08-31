package bean
{
	[RemoteClass(alias="com.gestaoequipamentos.bean.CadSegmentoBean")]
	public class CadSegmentoBean
	{
		private var _id:Number;
		private var _codigo:String;
		private var _descricao:String;
		private var _fator:String;
		
		public function get id(): Number{return _id};
		public function set id(id: Number): void{this._id = id}; 
		
		public function get codigo(): String{return _codigo};
		public function set codigo(codigo: String): void{this._codigo = codigo}; 
		
		public function get descricao(): String{return _descricao};
		public function set descricao(descricao: String): void{this._descricao = descricao}; 
		
		public function get fator(): String{return _fator};
		public function set fator(fator: String): void{this._fator = fator}; 
		
		
		public function CadSegmentoBean()
		{
		}
	}
}