package bean
{
	import mx.collections.ArrayCollection;

	[RemoteClass(alias="com.gestaoequipamentos.bean.SegmentoBean")]
	public class SegmentoBean
	{
		private var _idCheckin:Number;
		private var _numeroSegmento:String;
		private var _descricao:String;
		private var _cscc:String;
		private var _jbcd:String;
		private var _jbcdStr:String;
		private var _cptcd:String;
		private var _cptcdStr:String;
		private var _shopField:String;
		private var _jbctStr:String;
		private var _horas:String;
		private var _horasSubst:String;
		private var _pecasList: ArrayCollection;
		private var _id:Number;
		private var _operacaoList:ArrayCollection;
		private var _hasSendPecasDbsSegmento:String;
		private var _qtdComp:Number;
		private var _qtdCompSubst:Number;
		private var _msgDbs:String;
		private var _observacao:String;
		private var _idFuncionarioCriador:String;		
		private var _codErroDbs:String;		
		private var _havePecas:String;	
		private var _idFuncionarioPecas:String;
		private var _nomeFuncionarioPecas:String;
		private var _dataLiberacaoPecas:String;
		private var _dataTerminoServico:String;
		private var _numeroDoc:String;
		private var _pedido:String;
		private var _tituloFotos:String;
		private var _descricaoFalhaFotos:String;
		private var _conclusaoFotos:String;
		private var _idDocSegOper:Number;
		private var _idDocSegOperList:String;
		private var _nomeFuncionarioRemocao:String;
		private var _nomeFuncionarioEdicao:String;
		
		private var _segmentoList:ArrayCollection; 
		private var _dateOpen:String;
		private var _cliente:String;
		private var _numOs:String;
		private var _descricaojobControl:String;
		private var _modelo:String;
		private var _prazoPETS:String;
		private var _filial:String;
		private var _statusPrazo:String;
		private var _siglaStatusLegenda:String;
		private var _descricaoStatusLegenda:String;
		private var _dataHeader:String;
		private var _isMoreThirtyDay:String;
		private var _numSerie:String;
		private var _idEquipamento:String;
		private var _nomeSegmento:String;
		private var _observacaoStatusLegenda:String;
	
		
		public function get horasSubst():String
		{
			return _horasSubst;
		}

		public function set horasSubst(value:String):void
		{
			_horasSubst = value;
		}

		public function get qtdCompSubst():Number
		{
			return _qtdCompSubst;
		}

		public function set qtdCompSubst(value:Number):void
		{
			_qtdCompSubst = value;
		}

		public function get pedido():String
		{
			return _pedido;
		}

		public function set pedido(value:String):void
		{
			_pedido = value;
		}

		public function get dataTerminoServico():String
		{
			return _dataTerminoServico;
		}

		public function set dataTerminoServico(value:String):void
		{
			_dataTerminoServico = value;
		}

		public function get dataLiberacaoPecas():String
		{
			return _dataLiberacaoPecas;
		}

		public function set dataLiberacaoPecas(value:String):void
		{
			_dataLiberacaoPecas = value;
		}

		public function get numeroDoc():String
		{
			return _numeroDoc;
		}

		public function set numeroDoc(value:String):void
		{
			_numeroDoc = value;
		}

		public function get havePecas():String
		{
			return _havePecas;
		}

		public function set havePecas(value:String):void
		{
			_havePecas = value;
		}

		public function get observacao():String
		{
			return _observacao;
		}

		public function set observacao(value:String):void
		{
			_observacao = value;
		}

		public function get msgDbs():String
		{
			return _msgDbs;
		}

		public function set msgDbs(value:String):void
		{
			_msgDbs = value;
		}

		public function get descricao():String
		{
			return _descricao;
		}

		public function set descricao(value:String):void
		{
			_descricao = value;
		}

		public function get idCheckin():Number{return _idCheckin};
		public function set idCheckin(idCheckin:Number):void{this._idCheckin = idCheckin};
		
		public function get numeroSegmento():String{return _numeroSegmento};
		public function set numeroSegmento(numeroSegmento:String):void{this._numeroSegmento = numeroSegmento};
		
		public function get cscc():String{return _cscc};
		public function set cscc(cscc:String):void{this._cscc = cscc};
		
		public function get jbcd():String{return _jbcd};
		public function set jbcd(jbcd:String):void{this._jbcd = jbcd}; 

		public function get jbcdStr():String{return _jbcdStr};
		public function set jbcdStr(jbcdStr:String):void{this._jbcdStr = jbcdStr}; 
		
		public function get jbctStr():String{return _jbctStr};
		public function set jbctStr(jbctStr:String):void{this._jbctStr = jbctStr}; 
		
		public function get cptcd():String{return _cptcd};
		public function set cptcd(cptcd:String):void{this._cptcd= cptcd};

		public function get cptcdStr():String{return _cptcdStr};
		public function set cptcdStr(cptcdStr:String):void{this._cptcdStr = cptcdStr};
		
		public function get shopField():String{return _shopField};
		public function set shopField(shopField:String):void{this._shopField = shopField};

		public function get horas():String{return _horas};
		public function set horas(horas:String):void{this._horas = horas};

		public function get pecasList():ArrayCollection{return _pecasList};
		public function set pecasList(pecasList:ArrayCollection):void{this._pecasList = pecasList};
		
		public function get id():Number{return _id};
		public function set id(id:Number):void{this._id = id};
		
		public function get operacaoList():ArrayCollection{return _operacaoList};
		public function set operacaoList(operacaoList:ArrayCollection):void{this._operacaoList = operacaoList};

		public function get hasSendPecasDbsSegmento():String{return _hasSendPecasDbsSegmento};
		public function set hasSendPecasDbsSegmento(hasSendPecasDbsSegmento:String):void{this._hasSendPecasDbsSegmento = hasSendPecasDbsSegmento};

		public function get qtdComp():Number{return _qtdComp};
		public function set qtdComp(qtdComp:Number):void{this._qtdComp = qtdComp};
		
		public function get idFuncionarioCriador():String
		{
			return _idFuncionarioCriador;
		}

		public function set idFuncionarioCriador(value:String):void
		{
			_idFuncionarioCriador = value;
		}		

		public function get codErroDbs():String
		{
			return _codErroDbs;
		}

		public function set codErroDbs(value:String):void
		{
			_codErroDbs = value;
		}

		public function get idFuncionarioPecas():String
		{
			return _idFuncionarioPecas;
		}

		public function set idFuncionarioPecas(value:String):void
		{
			_idFuncionarioPecas = value;
		}

		public function get nomeFuncionarioPecas():String
		{
			return _nomeFuncionarioPecas;
		}

		public function set nomeFuncionarioPecas(value:String):void
		{
			_nomeFuncionarioPecas = value;
		}

		public function get tituloFotos():String
		{
			return _tituloFotos;
		}

		public function set tituloFotos(value:String):void
		{
			_tituloFotos = value;
		}

		public function get descricaoFalhaFotos():String
		{
			return _descricaoFalhaFotos;
		}

		public function set descricaoFalhaFotos(value:String):void
		{
			_descricaoFalhaFotos = value;
		}

		public function get conclusaoFotos():String
		{
			return _conclusaoFotos;
		}

		public function set conclusaoFotos(value:String):void
		{
			_conclusaoFotos = value;
		}

		public function get idDocSegOper():Number
		{
			return _idDocSegOper;
		}

		public function set idDocSegOper(value:Number):void
		{
			_idDocSegOper = value;
		}

		public function get nomeFuncionarioRemocao():String
		{
			return _nomeFuncionarioRemocao;
		}

		public function set nomeFuncionarioRemocao(value:String):void
		{
			_nomeFuncionarioRemocao = value;
		}

		public function get nomeFuncionarioEdicao():String
		{
			return _nomeFuncionarioEdicao;
		}

		public function set nomeFuncionarioEdicao(value:String):void
		{
			_nomeFuncionarioEdicao = value;
		}


		public function get dateOpen():String
		{
			return _dateOpen;
		}

		public function set dateOpen(value:String):void
		{
			_dateOpen = value;
		}

		public function get cliente():String
		{
			return _cliente;
		}

		public function set cliente(value:String):void
		{
			_cliente = value;
		}

		public function get numOs():String
		{
			return _numOs;
		}

		public function set numOs(value:String):void
		{
			_numOs = value;
		}

		public function get descricaojobControl():String
		{
			return _descricaojobControl;
		}

		public function set descricaojobControl(value:String):void
		{
			_descricaojobControl = value;
		}

		public function get modelo():String
		{
			return _modelo;
		}

		public function set modelo(value:String):void
		{
			_modelo = value;
		}

		public function get prazoPETS():String
		{
			return _prazoPETS;
		}

		public function set prazoPETS(value:String):void
		{
			_prazoPETS = value;
		}

		public function get filial():String
		{
			return _filial;
		}

		public function set filial(value:String):void
		{
			_filial = value;
		}

		public function get statusPrazo():String
		{
			return _statusPrazo;
		}

		public function set statusPrazo(value:String):void
		{
			_statusPrazo = value;
		}

		public function get siglaStatusLegenda():String
		{
			return _siglaStatusLegenda;
		}

		public function set siglaStatusLegenda(value:String):void
		{
			_siglaStatusLegenda = value;
		}

		public function get segmentoList():ArrayCollection
		{
			return _segmentoList;
		}

		public function set segmentoList(value:ArrayCollection):void
		{
			_segmentoList = value;
		}

		public function get dataHeader():String
		{
			return _dataHeader;
		}

		public function set dataHeader(value:String):void
		{
			_dataHeader = value;
		}

		public function get descricaoStatusLegenda():String
		{
			return _descricaoStatusLegenda;
		}

		public function set descricaoStatusLegenda(value:String):void
		{
			_descricaoStatusLegenda = value;
		}

		public function get isMoreThirtyDay():String
		{
			return _isMoreThirtyDay;
		}

		public function set isMoreThirtyDay(value:String):void
		{
			_isMoreThirtyDay = value;
		}

		public function get numSerie():String
		{
			return _numSerie;
		}

		public function set numSerie(value:String):void
		{
			_numSerie = value;
		}

		public function get idEquipamento():String
		{
			return _idEquipamento;
		}

		public function set idEquipamento(value:String):void
		{
			_idEquipamento = value;
		}

		public function get nomeSegmento():String
		{
			return _nomeSegmento;
		}

		public function set nomeSegmento(value:String):void
		{
			_nomeSegmento = value;
		}

		public function get observacaoStatusLegenda():String
		{
			return _observacaoStatusLegenda;
		}

		public function set observacaoStatusLegenda(value:String):void
		{
			_observacaoStatusLegenda = value;
		}

		public function get idDocSegOperList():String
		{
			return _idDocSegOperList;
		}

		public function set idDocSegOperList(value:String):void
		{
			_idDocSegOperList = value;
		}



		
		public function SegmentoBean()
		{
			_pecasList = new ArrayCollection();
			_operacaoList = new ArrayCollection();
		}
	}
}