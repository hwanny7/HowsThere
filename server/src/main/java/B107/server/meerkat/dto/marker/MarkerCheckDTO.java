package B107.server.meerkat.dto.marker;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
public class MarkerCheckDTO {
	private Long memberIdx;
	private boolean mc_check;
}
