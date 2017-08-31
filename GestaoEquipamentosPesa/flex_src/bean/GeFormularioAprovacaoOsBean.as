package bean
{
	import mx.collections.ArrayCollection;

	[RemoteClass(alias="com.gestaoequipamentos.bean.GeFormularioAprovacaoOsBean")]
	public class GeFormularioAprovacaoOsBean
	{
		private var _id:Number;
		private var _arranjoMaquina:String;
		private var _nomeContato:String;
		private var _telConcato:String;
		private var _horimetroPeca:Number;
		private var _entregaTecnica:String;
		private var _maquinaNova:String;
		private var _pecaVendBalcao:String;
		private var _pecaVendOs:String;
		private var _servicoRefeito:String;
		private var _nrNfVenda:String;
		private var _nrOsAnterior:String;
		private var _maquinaUsada:String;
		private var _pesaL:String;
		private var _dataVenda:String;
		private var _dataEntregaTecnica:String;
		private var _osCustomizacao:String;
		private var _descComponente:String;
		private var _descSintomasMaquina:String;
		private var _descricaoReparo:String;
		private var _fotoComponente:String;
		private var _amostraOleoSos:String;
		private var _testeLeakoff:String;
		private var _downloadServiceReportEt:String;
		private var _inspecaoMaterialRodanteCtps:String;
		private var _relatorioConsumoOeloLubrificante:String;
		private var _faltouFerramento:String;
		private var _consultouManualServico:String;
		private var _tempoReparoSuficiente:String;
		private var _componentePassouTeste:String;
		private var _recebeuTreinamento:String;
		private var _recebeuInformacaoCompletaReparo:String;
		private var _falhaGeradaDiagnosticoIncorreto:String;
		private var _clienteInfluenciouProposta:String;
		private var _foiConsultadoServicoOsAnterior:String;
		private var _foiCortadoPecasOrcamentoAnterior:String;
		private var _idCheckIn:Number;
		private var _tipoSolicitacao:String;
		private var _status:String;
		
		private var _codigoCliente:String;
		private var _cliente:String;
		private var _serie:String;
		private var _modelo:String;
		
		private var _emailContato:String;
		private var _material:String;
		private var _horimetro:String;
		
		private var _listaPecasAprovacao:ArrayCollection;
		
		
		
		private var _aplication:String;
		private var _ofCatEquip:String;
		private var _ofNonCatEquip:String;
		private var _equipDeliveryDate:String;
		private var _anualServiceSpend:String;
		private var _anualPartsSpend:String;
		private var _pnCauseFailuret:String;
		private var _groupCausingFailure:String;
		private var _machineHours:String;
		private var _dateOfFailure:String;
		private var _partHours:String;
		private var _complaint:String;
		private var _rootCauseTxt:String;
		private var _repairComplications:String;
		
		private var _correctiveActions:String;
		private var _partsCustomer:String;
		private var _laborCustomer:String;
		private var _miscCustomer:String;
		
		private var _partsDealer:String;
		private var _laborDealer:String;
		private var _miscDealer:String;
		
		private var _notes:String;
		private var _justificativaConcessao:String;
		private var _obsRejeicao:String;
		private var _nomeFilial:String;
		private var _tipoSistema:String;
		private var _outros:String;
		private var _filialCriacaoOS:String;
		private var _clientePesa:String;
		private var _responsavel:String;
		private var _dataCriacao:String;
		private var _numOs:String; 
		private var _funcAprovador:String;
		private var _maquinaParou:String;
		
		
		public function get maquinaParou():String
		{
			return _maquinaParou;
		}

		public function set maquinaParou(value:String):void
		{
			_maquinaParou = value;
		}

		public function get id(): Number{return _id};
		public function set id(id: Number): void{this._id = id}
		public function get arranjoMaquina():String
		{
			return _arranjoMaquina;
		}

		public function set arranjoMaquina(value:String):void
		{
			_arranjoMaquina = value;
		}

		public function get nomeContato():String
		{
			return _nomeContato;
		}

		public function set nomeContato(value:String):void
		{
			_nomeContato = value;
		}

		public function get telConcato():String
		{
			return _telConcato;
		}

		public function set telConcato(value:String):void
		{
			_telConcato = value;
		}

		public function get horimetroPeca():Number
		{
			return _horimetroPeca;
		}

		public function set horimetroPeca(value:Number):void
		{
			_horimetroPeca = value;
		}

		public function get entregaTecnica():String
		{
			return _entregaTecnica;
		}

		public function set entregaTecnica(value:String):void
		{
			_entregaTecnica = value;
		}

		public function get maquinaNova():String
		{
			return _maquinaNova;
		}

		public function set maquinaNova(value:String):void
		{
			_maquinaNova = value;
		}

		public function get pecaVendBalcao():String
		{
			return _pecaVendBalcao;
		}

		public function set pecaVendBalcao(value:String):void
		{
			_pecaVendBalcao = value;
		}

		public function get pecaVendOs():String
		{
			return _pecaVendOs;
		}

		public function set pecaVendOs(value:String):void
		{
			_pecaVendOs = value;
		}

		public function get servicoRefeito():String
		{
			return _servicoRefeito;
		}

		public function set servicoRefeito(value:String):void
		{
			_servicoRefeito = value;
		}

		public function get nrNfVenda():String
		{
			return _nrNfVenda;
		}

		public function set nrNfVenda(value:String):void
		{
			_nrNfVenda = value;
		}

		public function get nrOsAnterior():String
		{
			return _nrOsAnterior;
		}

		public function set nrOsAnterior(value:String):void
		{
			_nrOsAnterior = value;
		}

		public function get maquinaUsada():String
		{
			return _maquinaUsada;
		}

		public function set maquinaUsada(value:String):void
		{
			_maquinaUsada = value;
		}

		public function get pesaL():String
		{
			return _pesaL;
		}

		public function set pesaL(value:String):void
		{
			_pesaL = value;
		}

		public function get dataVenda():String
		{
			return _dataVenda;
		}

		public function set dataVenda(value:String):void
		{
			_dataVenda = value;
		}

		public function get dataEntregaTecnica():String
		{
			return _dataEntregaTecnica;
		}

		public function set dataEntregaTecnica(value:String):void
		{
			_dataEntregaTecnica = value;
		}

		public function get osCustomizacao():String
		{
			return _osCustomizacao;
		}

		public function set osCustomizacao(value:String):void
		{
			_osCustomizacao = value;
		}

		public function get descComponente():String
		{
			return _descComponente;
		}

		public function set descComponente(value:String):void
		{
			_descComponente = value;
		}

		public function get descSintomasMaquina():String
		{
			return _descSintomasMaquina;
		}

		public function set descSintomasMaquina(value:String):void
		{
			_descSintomasMaquina = value;
		}

		public function get descricaoReparo():String
		{
			return _descricaoReparo;
		}

		public function set descricaoReparo(value:String):void
		{
			_descricaoReparo = value;
		}

		public function get fotoComponente():String
		{
			return _fotoComponente;
		}

		public function set fotoComponente(value:String):void
		{
			_fotoComponente = value;
		}

		public function get amostraOleoSos():String
		{
			return _amostraOleoSos;
		}

		public function set amostraOleoSos(value:String):void
		{
			_amostraOleoSos = value;
		}

		public function get testeLeakoff():String
		{
			return _testeLeakoff;
		}

		public function set testeLeakoff(value:String):void
		{
			_testeLeakoff = value;
		}

		public function get downloadServiceReportEt():String
		{
			return _downloadServiceReportEt;
		}

		public function set downloadServiceReportEt(value:String):void
		{
			_downloadServiceReportEt = value;
		}

		public function get inspecaoMaterialRodanteCtps():String
		{
			return _inspecaoMaterialRodanteCtps;
		}

		public function set inspecaoMaterialRodanteCtps(value:String):void
		{
			_inspecaoMaterialRodanteCtps = value;
		}

		public function get relatorioConsumoOeloLubrificante():String
		{
			return _relatorioConsumoOeloLubrificante;
		}

		public function set relatorioConsumoOeloLubrificante(value:String):void
		{
			_relatorioConsumoOeloLubrificante = value;
		}

		public function get faltouFerramento():String
		{
			return _faltouFerramento;
		}

		public function set faltouFerramento(value:String):void
		{
			_faltouFerramento = value;
		}

		public function get consultouManualServico():String
		{
			return _consultouManualServico;
		}

		public function set consultouManualServico(value:String):void
		{
			_consultouManualServico = value;
		}

		public function get tempoReparoSuficiente():String
		{
			return _tempoReparoSuficiente;
		}

		public function set tempoReparoSuficiente(value:String):void
		{
			_tempoReparoSuficiente = value;
		}

		public function get componentePassouTeste():String
		{
			return _componentePassouTeste;
		}

		public function set componentePassouTeste(value:String):void
		{
			_componentePassouTeste = value;
		}

		public function get recebeuTreinamento():String
		{
			return _recebeuTreinamento;
		}

		public function set recebeuTreinamento(value:String):void
		{
			_recebeuTreinamento = value;
		}

		public function get recebeuInformacaoCompletaReparo():String
		{
			return _recebeuInformacaoCompletaReparo;
		}

		public function set recebeuInformacaoCompletaReparo(value:String):void
		{
			_recebeuInformacaoCompletaReparo = value;
		}

		public function get falhaGeradaDiagnosticoIncorreto():String
		{
			return _falhaGeradaDiagnosticoIncorreto;
		}

		public function set falhaGeradaDiagnosticoIncorreto(value:String):void
		{
			_falhaGeradaDiagnosticoIncorreto = value;
		}

		public function get clienteInfluenciouProposta():String
		{
			return _clienteInfluenciouProposta;
		}

		public function set clienteInfluenciouProposta(value:String):void
		{
			_clienteInfluenciouProposta = value;
		}

		public function get foiConsultadoServicoOsAnterior():String
		{
			return _foiConsultadoServicoOsAnterior;
		}

		public function set foiConsultadoServicoOsAnterior(value:String):void
		{
			_foiConsultadoServicoOsAnterior = value;
		}

		public function get foiCortadoPecasOrcamentoAnterior():String
		{
			return _foiCortadoPecasOrcamentoAnterior;
		}

		public function set foiCortadoPecasOrcamentoAnterior(value:String):void
		{
			_foiCortadoPecasOrcamentoAnterior = value;
		}

		public function get idCheckIn():Number
		{
			return _idCheckIn;
		}

		public function set idCheckIn(value:Number):void
		{
			_idCheckIn = value;
		}

		public function get tipoSolicitacao():String
		{
			return _tipoSolicitacao;
		}

		public function set tipoSolicitacao(value:String):void
		{
			_tipoSolicitacao = value;
		}

		public function get status():String
		{
			return _status;
		}

		public function set status(value:String):void
		{
			_status = value;
		}

		public function get codigoCliente():String
		{
			return _codigoCliente;
		}

		public function set codigoCliente(value:String):void
		{
			_codigoCliente = value;
		}

		public function get cliente():String
		{
			return _cliente;
		}

		public function set cliente(value:String):void
		{
			_cliente = value;
		}

		public function get serie():String
		{
			return _serie;
		}

		public function set serie(value:String):void
		{
			_serie = value;
		}

		public function get modelo():String
		{
			return _modelo;
		}

		public function set modelo(value:String):void
		{
			_modelo = value;
		}

		public function get listaPecasAprovacao():ArrayCollection
		{
			return _listaPecasAprovacao;
		}

		public function set listaPecasAprovacao(value:ArrayCollection):void
		{
			_listaPecasAprovacao = value;
		}

		public function get aplication():String
		{
			return _aplication;
		}

		public function set aplication(value:String):void
		{
			_aplication = value;
		}

		public function get ofCatEquip():String
		{
			return _ofCatEquip;
		}

		public function set ofCatEquip(value:String):void
		{
			_ofCatEquip = value;
		}

		public function get ofNonCatEquip():String
		{
			return _ofNonCatEquip;
		}

		public function set ofNonCatEquip(value:String):void
		{
			_ofNonCatEquip = value;
		}

		public function get equipDeliveryDate():String
		{
			return _equipDeliveryDate;
		}

		public function set equipDeliveryDate(value:String):void
		{
			_equipDeliveryDate = value;
		}

		public function get anualPartsSpend():String
		{
			return _anualPartsSpend;
		}

		public function set anualPartsSpend(value:String):void
		{
			_anualPartsSpend = value;
		}

		public function get pnCauseFailuret():String
		{
			return _pnCauseFailuret;
		}

		public function set pnCauseFailuret(value:String):void
		{
			_pnCauseFailuret = value;
		}

		public function get groupCausingFailure():String
		{
			return _groupCausingFailure;
		}

		public function set groupCausingFailure(value:String):void
		{
			_groupCausingFailure = value;
		}

		public function get machineHours():String
		{
			return _machineHours;
		}

		public function set machineHours(value:String):void
		{
			_machineHours = value;
		}

		public function get dateOfFailure():String
		{
			return _dateOfFailure;
		}

		public function set dateOfFailure(value:String):void
		{
			_dateOfFailure = value;
		}

		public function get partHours():String
		{
			return _partHours;
		}

		public function set partHours(value:String):void
		{
			_partHours = value;
		}

		public function get complaint():String
		{
			return _complaint;
		}

		public function set complaint(value:String):void
		{
			_complaint = value;
		}

		public function get rootCauseTxt():String
		{
			return _rootCauseTxt;
		}

		public function set rootCauseTxt(value:String):void
		{
			_rootCauseTxt = value;
		}

		public function get repairComplications():String
		{
			return _repairComplications;
		}

		public function set repairComplications(value:String):void
		{
			_repairComplications = value;
		}

		public function get correctiveActions():String
		{
			return _correctiveActions;
		}

		public function set correctiveActions(value:String):void
		{
			_correctiveActions = value;
		}

		public function get partsCustomer():String
		{
			return _partsCustomer;
		}

		public function set partsCustomer(value:String):void
		{
			_partsCustomer = value;
		}

		public function get laborCustomer():String
		{
			return _laborCustomer;
		}

		public function set laborCustomer(value:String):void
		{
			_laborCustomer = value;
		}

		public function get miscCustomer():String
		{
			return _miscCustomer;
		}

		public function set miscCustomer(value:String):void
		{
			_miscCustomer = value;
		}

		public function get partsDealer():String
		{
			return _partsDealer;
		}

		public function set partsDealer(value:String):void
		{
			_partsDealer = value;
		}

		public function get laborDealer():String
		{
			return _laborDealer;
		}

		public function set laborDealer(value:String):void
		{
			_laborDealer = value;
		}

		public function get miscDealer():String
		{
			return _miscDealer;
		}

		public function set miscDealer(value:String):void
		{
			_miscDealer = value;
		}

		public function get notes():String
		{
			return _notes;
		}

		public function set notes(value:String):void
		{
			_notes = value;
		}

		public function get justificativaConcessao():String
		{
			return _justificativaConcessao;
		}

		public function set justificativaConcessao(value:String):void
		{
			_justificativaConcessao = value;
		}

		public function get anualServiceSpend():String
		{
			return _anualServiceSpend;
		}

		public function set anualServiceSpend(value:String):void
		{
			_anualServiceSpend = value;
		}

		public function get emailContato():String
		{
			return _emailContato;
		}

		public function set emailContato(value:String):void
		{
			_emailContato = value;
		}


		public function get material():String
		{
			return _material;
		}

		public function set material(value:String):void
		{
			_material = value;
		}

		public function get horimetro():String
		{
			return _horimetro;
		}

		public function set horimetro(value:String):void
		{
			_horimetro = value;
		}

		public function get obsRejeicao():String
		{
			return _obsRejeicao;
		}

		public function set obsRejeicao(value:String):void
		{
			_obsRejeicao = value;
		}

		public function get nomeFilial():String
		{
			return _nomeFilial;
		}

		public function set nomeFilial(value:String):void
		{
			_nomeFilial = value;
		}

		public function get tipoSistema():String
		{
			return _tipoSistema;
		}

		public function set tipoSistema(value:String):void
		{
			_tipoSistema = value;
		}

		public function get outros():String
		{
			return _outros;
		}

		public function set outros(value:String):void
		{
			_outros = value;
		}

		public function get filialCriacaoOS():String
		{
			return _filialCriacaoOS;
		}

		public function set filialCriacaoOS(value:String):void
		{
			_filialCriacaoOS = value;
		}

		public function get clientePesa():String
		{
			return _clientePesa;
		}

		public function set clientePesa(value:String):void
		{
			_clientePesa = value;
		}

		public function get responsavel():String
		{
			return _responsavel;
		}

		public function set responsavel(value:String):void
		{
			_responsavel = value;
		}

		public function get dataCriacao():String
		{
			return _dataCriacao;
		}

		public function set dataCriacao(value:String):void
		{
			_dataCriacao = value;
		}

		public function get numOs():String
		{
			return _numOs;
		}

		public function set numOs(value:String):void
		{
			_numOs = value;
		}


		public function get funcAprovador():String
		{
			return _funcAprovador;
		}

		public function set funcAprovador(value:String):void
		{
			_funcAprovador = value;
		}


; 
		
		
		
		
		public function GeFormularioAprovacaoOsBean()
		{
			this._listaPecasAprovacao = new ArrayCollection();
		}
	}
}