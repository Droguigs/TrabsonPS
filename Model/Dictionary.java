/* Dicionario de caracteres para a hash */

package Model;

public interface Dictionary{




}
/* TRAP   = #00
*  FCMP   = #01
*  FUN    = #02
*  FAQL   = #03
*  FADD   = #04
*  FIX    = #05
*  FSUB   = #06
*  FIXU   = #07
*  FLOT   = #08|#09
*  FLOTU  = #0A|#0B
*  SFLOT  = #0C|#0D
*  SFLOTU = #0E|#0F
*
*  FMUL   = #10
*  FCMPE  = #11
*  FUNE   = #12
*  FEQLE  = #13
*  FDIV   = #14
*  FSQRT  = #15
*  FREM   = #16
*  FINT   = #17
*  MUL    = #18|#19
*  MULU   = #1A|#1B
*  DIV    = #1C|1D
*  DIVU   = #1E|1F
*
*  ADD    = #20|#21
*  ADDU   = #22|#23
*  SUB    = #24|#25
*  SUBU   = #26|#27
*  2ADDU  = #28|#29
*  4ADDU  = #2A|#2B
*  8ADDU  = #2C|#2D
*  16ADDU = #2E|#2F
*
*  CMP    = #30|#31
*  CMPU   = #32|#33
*  NEG    = #34|#35
*  NEGU   = #36|#37
*  SL     = #38|#39
*  SLU    = #3A|#3B
*  SR     = #3C|#3D
*  SRU    = #3E|#3F
*
*  BN     = #40|#41
*  BZ     = #42|#43
*  BP     = #44|#45
*  BOD    = #46|#47
*  BNN    = #48|#49
*  BNZ    = #4A|#4B
*  BNP    = #4C|#4D
*  BEV    = #4E|#4F
*
*  PBN    = #50|#51
*  PBZ    = #52|#53
*  PBP    = #54|#55
*  PBOD   = #56|#57
*  PBNN   = #58|#59
*  PBNZ   = #5A|#5B
*  PBNP   = #5C|#5D
*  PBEV   = #5E|#5F
*
*  CSN    = #60|#61
*  CSZ    = #62|#63
*  CSP    = #64|#65
*  CSOD   = #66|#67
*  CSNN   = #68|#69
*  CSNZ   = #6A|#6B
*  CSNP   = #6C|#6D
*  CSEV   = #6E|#6F
*
*  ZSN    = #70|#71
*  ZSZ    = #72|#73
*  ZSP    = #74|#75
*  ZSOD   = #76|#77
*  ZSNN   = #78|#79
*  ZSNZ   = #7A|#7B
*  ZSNP   = #7C|#7D
*  ZSEV   = #7E|#7F
*
*  LDB    = #80|#81
*  LDBU   = #82|#83
*  LDW    = #84|#85
*  LDWU   = #86|#87
*  LDT    = #88|#89
*  LDTU   = #8A|#8B
*  LDO    = #8C|#8D
*  LDOU   = #8E|#8F
*
*  LDSF   = #90|#91
*  LDHT   = #92|#93
*  CSWAP  = #94|#95
*  LDUNC  = #96|#97
*  LDVTS  = #98|#99
*  PRELD  = #9A|#9B
*  PREGO  = #9C|#9D
*  GO     = #9E|#9F
*
*  STB    = #A0|#A1
*  STBU   = #A2|#A3
*  STW    = #A4|#A5
*  STWU   = #A6|#A7
*  STT    = #A8|#A9
*  STTU   = #AA|#AB
*  STO    = #AC|#AD
*  STOU   = #AE|#AF
*
*  STSF   = #B0|#B1
*  STHT   = #B2|#B3
*  STCO   = #B4|#B5
*  STUNC  = #B6|#B7
*  SYNCD  = #B8|#B9
*  PREST  = #BA|#BB
*  SYNCID = #BC|#BD
*  PUSHGO = #BE|#BF
*
*  OR     = #C0|#C1
*  ORN    = #C2|#C3
*  NOR    = #C4|#C5
*  XOR    = #C6|#C7
*  AND    = #C8|#C9
*  ANDN   = #CA|#CB
*  NAND   = #CC|#CD
*  NXOR   = #CE|#CF
*
*  BDIF   = #D0|#D1
*  WDIF   = #D2|#D3
*  TDIF   = #D4|#D5
*  ODIF   = #D6|#D7
*  MUX    = #D8|#D9
*  SADD   = #DA|#DB
*  MOR    = #DC|#DD
*  MXOR   = #DE|#DF
*
*  SETH   = #E0
*  SETMH  = #E1
*  SETML  = #E2
*  SETL   = #E3
*  INCH   = #E4
*  INCMH  = #E5
*  INCML  = #E6
*  INCL   = #E7
*  ORH    = #E8
*  ORMH   = #E9
*  ORML   = #EA
*  ORL    = #EB
*  ANDNH  = #EC
*  ANDNMH = #ED
*  ANDNML = #EE
*  ANDNL  = #EF
*
*  JMP    = #F0|#F1
*  PUSHJ  = #F2|#F3
*  GETA   = #F4|#F5
*  PUT    = #F6|#F7
*  POP    = #F8
*  RESUME = #F9
*[UN]SAVE = #FA|#FB
*  SYNC   = #FC
*  SWYM   = #FD
*  GET    = #FE
*  TRIP   = #FF
* */