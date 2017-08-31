package bean
{
	[RemoteClass(alias="com.gestaoequipamentos.bean.TipoFreteBean")]
	public class TipoFreteBean
	{
		private var _id:Number;
		private var _tipoFrete:String;
		private var _taxa:String;
		private var _freteMinimo:String;	

		public function get id(): Number{return _id};
		public function set id(id: Number): void{this._id = id}; 
		
		public function get tipoFrete(): String{return _tipoFrete};
		public function set tipoFrete(tipoFrete: String): void{this._tipoFrete = tipoFrete}; 
		
		public function get taxa(): String{return _taxa};
		public function set taxa(taxa: String): void{this._taxa = taxa}; 
		
		public function get freteMinimo(): String{return _freteMinimo};
		public function set freteMinimo(freteMinimo: String): void{this._freteMinimo = freteMinimo}; 
		
	}
}