package util.enums;

import java.time.LocalDate;

public enum TipoRecorrencia {
	PONTUAL {
		@Override
		public LocalDate proximaData(LocalDate dataAtual) {
			return dataAtual;
		}
	},	
	
	SEMANAL {
		@Override
		public LocalDate proximaData(LocalDate dataAtual) {
			return dataAtual.plusWeeks(1);
		}
	},
	
	QUINZENAL {
		@Override
		public LocalDate proximaData(LocalDate dataAtual) {
			return dataAtual.plusWeeks(2);
		}
	},
	
	MENSAL {
		@Override
		public LocalDate proximaData(LocalDate dataAtual) {
			return dataAtual.plusMonths(1);
		}
	},
	
	CANCELADO {
		@Override
		public LocalDate proximaData(LocalDate dataAtual) {
			throw new UnsupportedOperationException("Agendamentos cancelados não têm próxima data.");
		}
	};
	
	public abstract LocalDate proximaData(LocalDate dataAtual);
}