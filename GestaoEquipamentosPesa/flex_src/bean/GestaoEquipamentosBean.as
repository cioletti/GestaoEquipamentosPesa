package bean
{
	import mx.collections.ArrayCollection;

	[RemoteClass(alias="com.gestaoequipamentos.bean.GestaoEquipamentosBean")]
	public class GestaoEquipamentosBean
	{
		private var _idCheckIn:Number;
		private var _msg:String;		
		private var _filial:String;
		private var _jobControl:String;
		private var _codigoCliente:String;
		private var _estimateBy:String;
		private var _make:String;
		private var _numeroSerie:String;
		private var _horimetro:String;
		private var _idOsPalm:Number;
		private var _segmento:String;
		private var _centroDeCusto:String;
		private var _jobCode:String;
		private var _componenteCode:String;		
		private var _numContrato:String;		
		private var _bgrp:String;		
		private var _vcc:ValidarCentroDeCustoContaContabilBean;
		private var _agendamentoBean:AgendamentoBean;
		private var _segmentoList:ArrayCollection;
		private var _nomeCliente:String;
		
		public function get nomeCliente():String
		{
			return _nomeCliente;
		}

		public function set nomeCliente(value:String):void
		{
			_nomeCliente = value;
		}

		public function get filial(): String{return _filial};
		public function set filial(filial: String): void{this._filial = filial};
		
		public function get jobControl(): String{return _jobControl};
		public function set jobControl(jobControl: String): void{this._jobControl = jobControl};
		
		public function get codigoCliente(): String{return _codigoCliente};
		public function set codigoCliente(codigoCliente: String): void{this._codigoCliente = codigoCliente};
		
		public function get estimateBy(): String{return _estimateBy};
		public function set estimateBy(estimateBy: String): void{this._estimateBy = estimateBy};
		
		public function get make(): String{return _make};
		public function set make(make: String): void{this._make = make};
		
		public function get numeroSerie(): String{return _numeroSerie};
		public function set numeroSerie(numeroSerie: String): void{this._numeroSerie = numeroSerie};
		
		public function get horimetro(): String{return _horimetro};
		public function set horimetro(horimetro: String): void{this._horimetro = horimetro};
		
		public function get idOsPalm(): Number{return _idOsPalm};
		public function set idOsPalm(idOsPalm: Number): void{this._idOsPalm = idOsPalm}; 
		
		public function get segmento(): String{return _segmento};
		public function set segmento(segmento: String): void{this._segmento = segmento};
		
		
		public function get jobCode(): String{return _jobCode};
		public function set jobCode(jobCode: String): void{this._jobCode = jobCode};
		
		public function get componenteCode(): String{return _componenteCode};
		public function set componenteCode(componenteCode: String): void{this._componenteCode = componenteCode};

		public function get vcc(): ValidarCentroDeCustoContaContabilBean{return _vcc};
		public function set vcc(vcc: ValidarCentroDeCustoContaContabilBean): void{this._vcc = vcc};
		
		public function get msg(): String{return _msg};
		public function set msg(msg: String): void{this._msg = msg};

		public function get bgrp(): String{return _bgrp};
		public function set bgrp(bgrp: String): void{this._bgrp = bgrp};

		public function get centroDeCusto(): String{return _centroDeCusto};
		public function set centroDeCusto(centroDeCusto: String): void{this._centroDeCusto = centroDeCusto};

		public function get numContrato(): String{return _numContrato};
		public function set numContrato(numContrato: String): void{this._numContrato = numContrato};

		public function get agendamentoBean(): AgendamentoBean{return _agendamentoBean};
		public function set agendamentoBean(agendamentoBean: AgendamentoBean): void{this._agendamentoBean = agendamentoBean};

		public function get segmentoList(): ArrayCollection{return _segmentoList};
		public function set segmentoList(segmentoList: ArrayCollection): void{this._segmentoList = segmentoList};

		public function get idCheckIn(): Number{return _idCheckIn};
		public function set idCheckIn(idCheckIn: Number): void{this._idCheckIn = idCheckIn};
		
		
		
		
		public function GestaoEquipamentosBean()
		{
			_segmentoList = new ArrayCollection();
		}
	}
}