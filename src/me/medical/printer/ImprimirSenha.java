package me.medical.printer;

import me.medical.model.SenhaBean;
import me.medical.utils.Utils;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 *
 * @author Eleton Breke
 * @email eletonbreke@yahoo.com.br
 * @version 1.0
 * @since
 *
 */
public class ImprimirSenha {

    byte[] comecoNegrito = {0x1B, 0x45};
    byte[] fimNegrito = {0x1B, 0x46};
    byte[] comecoExp = {0x1B, 0x0E};
    byte[] fimExp = {0x1B, 0x14};
    byte[] comecoCond = {0x1B, 0x0F};
    byte[] fimCond = {0x1B, 0x12};
    byte[] comecoNorm = {0x1B, 0x4D};
    byte[] comecoItal = {0x1B, 0x34};
    byte[] fimItal = {0x1B, 0x35};
    byte[] comecoElite = {0x1B, 0x50};
    byte[] subinhado = {0x1B, 0x2D};

    private static final char ESC = 27; //escape
    private static final char AT = 64; //@
    private static final char LINE_FEED = 10; //line feed/new line
    private static final char PARENTHESIS_LEFT = 40;
    private static final char BACKSLASH = 92;
    private static final char CR = 13; //carriage return
    private static final char TAB = 9; //horizontal tab
    private static final char FF = 12; //form feed
    private static final char P = 80; //10cpi pitch
    private static final char M = 77; //12cpi pitch
    private static final char g = 103; //15cpi pitch
    private static final char p = 112; //used for choosing proportional mode or fixed-pitch
    private static final char t = 116; //used for character set assignment/selection
    private static final char l = 108; //used for setting left margin
    private static final char x = 120; //used for setting draft or letter quality (LQ) printing
    private static final char E = 69; //bold font on
    private static final char F = 70; //bold font off
    private static final char J = 74; //used for advancing paper vertically
    private static final char Q = 81; //used for setting right margin
    private static final char $ = 36; //used for absolute horizontal positioning
    public static final char ITALIC_ON = 52; //set font italic
    public static final char ITALIC_OFF = 53; //unset font italic
    public static final char CONDENSED_ON = 15;
    public static final char CONDENSED_OFF = 18;

    public void imprimirSenha(SenhaBean emp) {
        Imprimir p = new Imprimir();
        try {
            String impressao
                    = "================================================\n"
                    + "*********** SENHA PARA ATENDIMENTO *************\n"
                    + "================================================\n\n";
            impressao += "" + (char) 27 + (char) 33 + (char) 60 + Utils.center(emp.getSenha(), 24) + (char) 27 + (char) 33 + (char) 0;//expande o texto
             impressao += "                                                \n";
            impressao += "\n________________________________________________\n";
            impressao += Utils.center("AGUARDE SER CHAMADO PELO PAINEL ELETRONICO", 48) + "\n";
            impressao += "================================================\n";
            impressao += Utils.center(Utils.convertData(new Date(), "dd/MM/yyyy  hh:mm:ss"), 42) + "\n";
            impressao += "================================================\n";
            impressao += "                                                \n\n\n";

            p.imprime(impressao);
            p.cortar();

            System.out.println(impressao);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

//
//    public void imprimirVendaBematech(int idVenda, double dinheiro) {
//        Imprimir p = new Imprimir();
//        List<ParcelaReceberBean> lprb = new ArrayList<>();
//        try {
//            VendaBean v = new VendaDao().getBanco(idVenda);
//            //  VendaProdutoBean vp = new VendaProdutoDao().getBanco(idVenda);
//
//            FilialBean f = new FilialDao().getFilial(v.getFilial().getIdFilial());
//            String impressao
//                    = "=========================================\n"
//                    + f.getNome().toUpperCase() + "\n"
//                    + f.getCidade().getNome().toUpperCase() + " - " + f.getCidade().getUf().toUpperCase() + "   Tel: " + f.getTelefone() + "\n"
//                    + "==========================================\n"
//                    + "********* N�O � DOCUMENTO FISCAL *********\n"
//                    + "==========================================\n"
//                    + "PRODUTO              QTDE VALOR UN   VALOR\n";
//
//            listaProduto = new VendaProdutoDao().getListaVenda(idVenda);
//            for (VendaProdutoBean venda : listaProduto) {
//                double valor = (venda.getValorVenda() * venda.getQuantidade());
//                impressao += String.format("%-19.19s", venda.getCodigoBarras().getProdutodescritivo().getProduto().getNome())
//                        + String.format("%6.6s", Util.convertDouble(venda.getQuantidade()))
//                        + String.format("%9.9s", Util.convertDouble(venda.getValorVenda()))
//                        + String.format("%9.9s", String.valueOf(Util.convertDouble(valor)) + "\n");
//            }
//
//            impressao += "------------------------------------------\n";
//            impressao += "                         TOTAL: R$" + String.format("%9.9s", String.valueOf(Util.convertDouble(v.getTotal())) + "\n");
//
//            if (dinheiro != 0) {
//                impressao += "                      DINHEIRO: R$" + String.format("%9.9s", String.valueOf(Util.convertDouble(dinheiro)) + "\n");
//                impressao += "------------------------------------------\n";
//                impressao += "                         TROCO: R$" + String.format("%9.9s", String.valueOf(Util.convertDouble(dinheiro - v.getTotal())) + "\n");
//                impressao += "------------------------------------------\n";
//                impressao += "CLIENTE: " + String.format("%-33.33s", v.getPessoa().getNome().toUpperCase()) + "\n";
//            } else {
//                impressao += "------------------------------------------\n";
//                impressao += "CLIENTE: " + String.format("%-33.33s", v.getPessoa().getNome().toUpperCase()) + "\n";
//                impressao += "-----------FORMA DE PAGAMENTO-------------\n";
//                lprb = new ParcelaReceberDao().getListVenda(v.getIdVenda());
//                int i = 1;
//                for (ParcelaReceberBean pr : lprb) {
//                    impressao += (i++) + "� Vencimento:" + Util.convertData(pr.getVencimento()) + " Valor: R$" + String.format("%9.9s", String.valueOf(Util.convertDouble(pr.getValor()))) + "\n";
//                }
//            }
//            impressao += "------------------------------------------\n";
//            impressao += "VENDEDOR(A): "+String.format("%-29.29s",v.getFuncionario().getPessoa().getNome().toUpperCase().split("\\ ")[0])+"\n";
//            impressao += "==========================================\n";
//            impressao += "     AGRADECEMOS PELA SUA PREFER�NCIA     \n";
//            impressao += "==========================================\n";
//            impressao += "*********** " + Util.convertDataTime(new Date()) + " **********\n";
//            impressao += "==========================================\n";
//            impressao += "\n\n\n";
//            System.out.println(Util.removeAccents(impressao));
//
//            p.imprime(Util.removeAccents(impressao));
//            p.cortar();
//            
//            //emite comprovante
//            if (dinheiro == 0) {
//                if (Msg.confirmar(null, "Deseja Emitir Guia de Pagamento?")) {
//                    String forma
//                            = "=========================================\n"
//                            + f.getNome().toUpperCase() + "\n"
//                            + f.getCidade().getNome().toUpperCase() + " - " + f.getCidade().getUf().toUpperCase() + "   Tel: " + f.getTelefone() + "\n"
//                            + "==========================================\n"
//                            + "********* N�O � DOCUMENTO FISCAL *********\n"
//                            + "==========================================\n"
//                            + "N        VENCIMENTO           VALOR       \n";
//                    int i = 1;
//                   // lprb = new ParcelaReceberDao().getListVenda(v.getIdVenda());
//                    for (ParcelaReceberBean pr : lprb) {
//                        forma += String.format("%-2.2s", String.valueOf((i++)))
//                                + "       " + Util.convertData(pr.getVencimento())
//                                + "           R$" + String.format("%10.10s", String.valueOf(Util.convertDouble(pr.getValor()))) + "\n";
//                    }
//                    forma += "==========================================\n";
//                    forma += "Ass:                                      \n";
//                    forma += "------------------------------------------\n";
//                    forma += "CLIENTE: " + String.format("%-33.33s", v.getPessoa().getNome().toUpperCase()) + "\n";
//                    forma += "==========================================\n";
//                    forma += "*********** " + Util.convertDataTime(new Date()) + " **********\n";
//                    forma += "==========================================\n";
//                    forma += "\n\n\n";
//                    System.out.println(forma);
//                    p.imprime(Util.removeAccents(forma));
//                    p.cortar();
//                }
//            }
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//    }
}
