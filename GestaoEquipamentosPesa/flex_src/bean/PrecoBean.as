package bean
{
	import mx.collections.ArrayCollection;

	[RemoteClass(alias="com.gestaoequipamentos.bean.PrecoBean")]
	public class PrecoBean
	{
		private var _id:Number;
		private var _descricaoCompCode:String;		
		private var _jobCode:String;
		private var _idJobCode:Number;		
		private var _descricaoJobCode:String;
		private var _jobCodeDescricao:String;
		private var _compCode:String;
		private var _idPrefixo:Number;
		private var _idModelo:Number;
		private var _modeloStr:String;
		private var _prefixoStr:String;
		private var _qtdHoras:String;
		private var _idComplexidade:Number;
		private var _complexidadeStr:String;
		private var _codCliente:String;
		private var _orcamentoList:ArrayCollection;
		private var _isUrgente:String;
		private var _qtdItens:Number;
		private var _descricaoServico:String;
		
		
		public function get qtdItens(): Number{return _qtdItens};
		public function set qtdItens(qtdItens: Number): void{this._qtdItens = qtdItens};	
		
		public function get id(): Number{return _id};
		public function set id(id: Number): void{this._id = id}; 
		
		public function get descricaoCompCode(): String{return _descricaoCompCode};
		public function set descricaoCompCode(descricaoCompCode: String): void{this._descricaoCompCode = descricaoCompCode}; 
		
		public function get jobCode(): String{return _jobCode};
		public function set jobCode(jobCode: String): void{this._jobCode = jobCode};
		
		public function get idJobCode(): Number{return _idJobCode};
		public function set idJobCode(idJobCode: Number): void{this._idJobCode = idJobCode};
		
		public function get compCode(): String{return _compCode};
		public function set compCode(compCode: String): void{this._compCode = compCode};
		
		public function get idPrefixo(): Number{return _idPrefixo};
		public function set idPrefixo(idPrefixo: Number): void{this._idPrefixo = idPrefixo}; 
		
		public function get idModelo(): Number{return _idModelo};
		public function set idModelo(idModelo: Number): void{this._idModelo = idModelo}; 
		
		public function get modeloStr(): String{return _modeloStr};
		public function set modeloStr(modeloStr: String): void{this._modeloStr = modeloStr};
		
		public function get prefixoStr(): String{return _prefixoStr};
		public function set prefixoStr(prefixoStr: String): void{this._prefixoStr = prefixoStr};
		
		public function get qtdHoras(): String{return _qtdHoras};
		public function set qtdHoras(qtdHoras: String): void{this._qtdHoras = qtdHoras};
		
		public function get idComplexidade(): Number{return _idComplexidade};
		public function set idComplexidade(idComplexidade: Number): void{this._idComplexidade = idComplexidade};
		
		public function get complexidadeStr(): String{return _complexidadeStr};
		public function set complexidadeStr(complexidadeStr: String): void{this._complexidadeStr = complexidadeStr};

		public function get descricaoJobCode(): String{return _descricaoJobCode};
		public function set descricaoJobCode(descricaoJobCode: String): void{this._descricaoJobCode = descricaoJobCode};
		
		public function get codCliente(): String{return _codCliente};
		public function set codCliente(codCliente: String): void{this._codCliente = codCliente};
		
		public function get jobCodeDescricao(): String{return _jobCodeDescricao};
		public function set jobCodeDescricao(jobCodeDescricao: String): void{this._jobCodeDescricao = jobCodeDescricao};
		
		public function get orcamentoList():ArrayCollection{return _orcamentoList};
		public function set orcamentoList(orcamentoList:ArrayCollection):void{this._orcamentoList = orcamentoList};
		
		public function get isUrgente():String{return _isUrgente};
		public function set isUrgente(isUrgente:String):void{this._isUrgente = isUrgente};
		
		public function get descricaoServico(): String{return _descricaoServico};
		public function set descricaoServico(descricaoServico: String): void{this._descricaoServico = descricaoServico};
		
		public function PrecoBean(){
			_orcamentoList = new ArrayCollection();
			
		}
	}
}
