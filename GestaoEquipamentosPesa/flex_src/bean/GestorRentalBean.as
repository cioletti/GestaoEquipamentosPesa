package bean
{
	[RemoteClass(alias="com.gestaoequipamentos.bean.GestorRentalBean")]
	public class GestorRentalBean
	{
		private var _id:Number;
		private var _nome: String;
		private var _email: String;
		private var _filial: Number;
		
		public function GestorRentalBean(){}
		
		public function get id(): Number{return _id};
		public function set id(nome: Number): void{this._id = id}; 
		
		public function get nome(): String{return _nome};
		public function set nome(nome: String): void{this._nome = nome}; 
		
		public function get email(): String{return _email};
		public function set email(email: String): void{this._email = email}; 
		
		public function get filial(): Number{return _filial};
		public function set filial(filial: Number): void{this._filial = filial}; 
	}
}