package bean
{
	import mx.collections.ArrayCollection;

	[RemoteClass(alias="com.gestaoequipamentos.bean.AgendamentoBean")]
	public class AgendamentoBean
	{
		private var _id:Number;
		private var _idContrato:Number;
		private var _numSerie:String;
		private var _horimetro:Number;
		private var _horasPendentes:Number;
		private var _codigoCliente:String;
		private var _modelo:String;
		private var _idStatusAgendamento:Number;
		private var _idContHorasStandard:Number;
		private var _idFuncionario:String;
		private var _idConfOperacional:Number;
		private var _horasRevisao:Number;
		private var _dataAgendamento:String;
		private var _dataAgendamentoFinal:String;
		private var _siglaStatus:String;
		private var _numOs:String;
		private var _agendamentoList:ArrayCollection;
		private var _local:String;
		private var _contato:String;
		private var _numContrato:String;
		private var _telefone:String;
		private var _statusAgendamento:String;
		private var _standardJob:String;
		private var _filial:String;
		private var _filialDestino:String;
		private var _siglaTipoContrato:String;
		private var _pecasList:ArrayCollection;
		private var _dataAtualizacaoHorimetro:String;
		private var _razaoSocial:String;
		
		public function get id(): Number{return _id};
		public function set id(id: Number): void{this._id = id}; 
		
		public function get idContrato(): Number{return _idContrato};
		public function set idContrato(idContrato: Number): void{this._idContrato = idContrato}; 

		public function get numSerie(): String{return _numSerie};
		public function set numSerie(numSerie: String): void{this._numSerie = numSerie}; 

		public function get horimetro(): Number{return _horimetro};
		public function set horimetro(horimetro: Number): void{this._horimetro = horimetro}; 

		public function get horasPendentes(): Number{return _horasPendentes};
		public function set horasPendentes(horasPendentes: Number): void{this._horasPendentes = horasPendentes};	

		public function get codigoCliente(): String{return _codigoCliente};
		public function set codigoCliente(codigoCliente: String): void{this._codigoCliente = codigoCliente};	

		public function get idStatusAgendamento(): Number{return _idStatusAgendamento};
		public function set idStatusAgendamento(idStatusAgendamento: Number): void{this._idStatusAgendamento = idStatusAgendamento}; 

		public function get idContHorasStandard(): Number{return _idContHorasStandard};
		public function set idContHorasStandard(idContHorasStandard: Number): void{this._idContHorasStandard = idContHorasStandard}; 
		
		public function get idFuncionario(): String{return _idFuncionario};
		public function set idFuncionario(idFuncionario: String): void{this._idFuncionario = idFuncionario}; 

		public function get idConfOperacional(): Number{return _idConfOperacional};
		public function set idConfOperacional(idConfOperacional: Number): void{this._idConfOperacional = idConfOperacional}; 

		public function get horasRevisao(): Number{return _horasRevisao};
		public function set horasRevisao(horasRevisao: Number): void{this._horasRevisao = horasRevisao}; 
		
		public function get dataAgendamento(): String{return _dataAgendamento};
		public function set dataAgendamento(dataAgendamento: String): void{this._dataAgendamento = dataAgendamento}; 

		public function get dataAgendamentoFinal(): String{return _dataAgendamentoFinal};
		public function set dataAgendamentoFinal(dataAgendamentoFinal: String): void{this._dataAgendamentoFinal = dataAgendamentoFinal}; 

		public function get siglaStatus(): String{return _siglaStatus};
		public function set siglaStatus(siglaStatus: String): void{this._siglaStatus = siglaStatus}; 

		public function get numOs(): String{return _numOs};
		public function set numOs(numOs: String): void{this._numOs = numOs}; 

		public function get local(): String{return _local};
		public function set local(local: String): void{this._local = local}; 

		public function get contato(): String{return _contato};
		public function set contato(contato: String): void{this._contato = contato}; 

		public function get numContrato(): String{return _numContrato};
		public function set numContrato(numContrato: String): void{this._numContrato = numContrato}; 

		public function get telefone(): String{return _telefone};
		public function set telefone(telefone: String): void{this._telefone = telefone}; 

		public function get modelo(): String{return _modelo};
		public function set modelo(modelo: String): void{this._modelo = modelo}; 

		public function get statusAgendamento(): String{return _statusAgendamento};
		public function set statusAgendamento(statusAgendamento: String): void{this._statusAgendamento = statusAgendamento}; 

		public function get dataAtualizacaoHorimetro(): String{return _dataAtualizacaoHorimetro};
		public function set dataAtualizacaoHorimetro(dataAtualizacaoHorimetro: String): void{this._dataAtualizacaoHorimetro = dataAtualizacaoHorimetro}; 

		public function get standardJob(): String{return _standardJob};
		public function set standardJob(standardJob: String): void{this._standardJob = standardJob}; 

		public function get filial(): String{return _filial};
		public function set filial(filial: String): void{this._filial = filial}; 
		
		public function get filialDestino(): String{return _filialDestino};
		public function set filialDestino(filialDestino: String): void{this._filialDestino = filialDestino}; 


		public function get siglaTipoContrato(): String{return _siglaTipoContrato};
		public function set siglaTipoContrato(siglaTipoContrato: String): void{this._siglaTipoContrato = siglaTipoContrato}; 

		public function get agendamentoList(): ArrayCollection{return _agendamentoList};
		public function set agendamentoList(agendamentoList: ArrayCollection): void{this._agendamentoList = agendamentoList}; 

		public function get pecasList(): ArrayCollection{return _pecasList};
		public function set pecasList(pecasList: ArrayCollection): void{this._pecasList = pecasList}; 

		public function get razaoSocial(): String{return _razaoSocial};
		public function set razaoSocial(razaoSocial: String): void{this._razaoSocial = razaoSocial}; 
		
		
		public function AgendamentoBean()
		{
		}
	}
}