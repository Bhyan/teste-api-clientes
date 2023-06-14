package domain;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class Client {
    @Builder.Default
    private int id = 1005;
    @Builder.Default
    private String nome = "Minnie Mouse";
    @Builder.Default
    private int idade = 25;
    @Builder.Default
    private int risco = 0;
}
