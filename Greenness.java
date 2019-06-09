package implementacoes;

import java.awt.Color;
import java.awt.image.BufferedImage;
import javax.swing.JOptionPane;
import swing.janelas.PDI_Lote;

/**
 * Classe para os métodos(funcoes) para o calculo do Greenness, onde serão mantidos
 * , que serao usadas pelo resto do programa.
 * 
 * @author Flavia Mattos & Arthur Costa
 */
public class Greenness {

/**
 * Essa função é a implementação da método de GreennesskG = kG − (R + B)
 * onde K é o valor passado pelo usuário e o R,G e B são os valores obtido da imagem
 * 
 * @param img A imagem onde o filtro será aplicado
 * @param K O valor K da equação
 * @return retorna a imagem depois de aplicado o filtro
 */
public BufferedImage GreennKG(BufferedImage img, float K) {
	 // Par�metro de Limiar de Ilumina��o It{
    double it;
    
    //calculo de Par�metro de Limiar de Ilumina��o It{
    
  
    
    //criacao da imagem  media e calculo do numerador de it{
    BufferedImage media = new BufferedImage(img.getWidth(), img.getHeight(), img.getType());//imagem da media
	for(int soma=0 , i=1 ; i<img.getWidth()-1 ; i++) {
		for(int j=1  ; j<img.getHeight()-1; j++) { 
			
			for(int lin=i-1; lin<=i+1; lin++) {
				for(int col=j-1; col<=j+1; col++) {
					Color x = new Color(img.getRGB(col , lin));
		    		soma += (int)x.getRed();//soma todos os valores em uma mascara 3x3
		    	}
			}
			//Color central = new Color(img.getRGB(i, j));
			//soma -= central.getRed();// pixel central eh extraido da soma total mascara 3x3
			soma = (int)soma/9;//valor da media dos pixel vizinhos mascara 3x3
			Color x = new Color(soma,soma,soma);
			media.setRGB(j,i,x.getRGB());
			soma=0;
		}
	}
	//preenche as fronteiras da imagem media{
	for(int j=1  ; j<img.getWidth()-1; j++) { 
		Color x = new Color(media.getRGB(j , 1));
		media.setRGB(j, 0, x.getRGB());
	}
	for(int i=0  ; i<img.getHeight()-1; i++) { 
		Color x = new Color(media.getRGB(i , img.getWidth()-2));
		media.setRGB(img.getWidth()-1 ,i, x.getRGB());
	}
	for(int j=1  ; j<img.getWidth(); j++) { 
		Color x = new Color(media.getRGB(img.getHeight()-2 , j));
		media.setRGB(j, img.getHeight()-1, x.getRGB());
	}
	for(int i=0  ; i<img.getHeight(); i++) { 
		Color x = new Color(media.getRGB(1 , i));
		media.setRGB(0,i , x.getRGB());
	}
	//}preenche as fronteiras da imagem media
	
	//calculo do numerador{
	double numerador=0;
	for(int i=0;i<img.getHeight();i++) {
		for(int j=0;j<img.getWidth();j++) {
			Color x = new Color(media.getRGB(j , i));
			Color central = new Color(img.getRGB(j, i));
			numerador+= Math.pow(central.getRed()-x.getRed(),2);
		}
	}
	//}calculo do numerador
	

	//}criacao da imagem  media e calculo do numerador de it
	
	//calcula denominador{
	//BufferedImage denominador = new BufferedImage(img.getWidth(), img.getHeight(), img.getType());//imagem auxiliar do denominador It
	double soma=0;
	double diferenca=0;
	double denominador=0;
	for(int i=1 ; i<img.getWidth()-1 ; i++) {
		for(int j=1  ; j<img.getHeight()-1; j++) { 

			for(int lin=i-1; lin<=i+1; lin++) {
				for(int col=j-1; col<=j+1; col++) {
					Color central = new Color(img.getRGB(j, i));
					Color x = new Color(img.getRGB(lin , col));
					diferenca += Math.pow( (central.getRed() - x.getRed() ),2);
		    	}
			}	
			if((i==1 && j==1) || (i==1 && j==img.getWidth()-2) || (i==img.getHeight()-2 && j==img.getWidth()-2) || (i==img.getHeight()-2 && j==1))
				soma += 4* (diferenca/9);//incrementa a (diferenca do pixel central (i,j) pelo seu pixel vizinho (lin,col))^2
			else if(i==1 || i==img.getHeight()-2 || j==1 || j==img.getWidth()-2)
				soma += 2* (diferenca/9);//incrementa a (diferenca do pixel central (i,j) pelo seu pixel vizinho (lin,col))^2
			else
				soma += (diferenca/9);//incrementa a (diferenca do pixel central (i,j) pelo seu pixel vizinho (lin,col))^2
			denominador+=soma;
			diferenca=0;
			soma=0;
		}
	}
	
	
	//}calcula denominador
	 it=(numerador/(denominador)); 
	System.out.printf("%d   ",(int)(it*256));
	//}calculo de Par�metro de Limiar de Ilumina��o It
	
	

	
	
	//criacao da imagem diferenca
	
	/*		Color x = new Color(img.getRGB(i,j));
			soma -= x.getRed();//subtrai pixel central
			soma = soma/8;//valor da media dos pixel vizinhos de i,j
			Numerador += soma*soma;//incrementa todas as medias dos pixels vizinhos ao central mascara 3x3 e eleva ao quadrado
			soma=0;*/
	//}Numerador de IT
	
	/*//Denominador de IT{
	double denominador=0;
	for(int i=1 ; i<img.getWidth()-1 ; i++) {
		for(int j=1; j<img.getHeight()-1; j++) { 
			
		
			for(double difereca=0 , int lin=i-1; lin<i+1; lin++) {
				for(int col=j-1; col<j+1; col++) {
				diferenca = img.getRGB(i,j) - img.getRGB(lin,col);
				}
			}
		}
	}*/
	//}Denominador de IT
	
    //}calculo de Par�metro de Limiar de Ilumina��o It
	
    //Histograma original
    double original[] = new double[256];//histograma original da imagem
    for (int i = 0 ;i<256; i++)
    	original[i]=0;
    
    for (int i = 0; i < img.getWidth(); i++) {
    	// for acima e abaixo servem para preencher os valores do histograma original
        for (int j = 0; j < img.getHeight(); j++) {
        	Color x = new Color(img.getRGB(j , i));
        	original[x.getRed()] += 1;
        }
    }
    
    //Histograma Plower
    int sizeLow=(int)((256*it)+1);//tamanho do vetor Plower, quantidade de niveis de cinza de Plower
    double Plower[] = new double[sizeLow];//parte do histograma mais escuro (0 ate it)
    for (int i = 0 ;i<sizeLow; i++)
    	Plower[i]=original[i];

    //Histograma Pupper
    int sizeUpp=(int)(256-(it*256));//tamanho do vetor Pupper, quantidade de niveis de cinza de Pupper
    double Pupper[] = new double[sizeUpp];//parte do histograma mais claro (it+1 ate 255)
    for (int i = sizeLow ;i<sizeUpp; i++)
    	Pupper[i]=original[i];
    
    
    //calculos de Probabilidade simples{
    
    //probabilidade de Plower{
    int NPixLow=0;//Numero de pixels totais de Plower
    for (int i = 0 ;i<sizeLow; i++)
    	NPixLow += Plower[i];
    
    double probabilidadeLow[] = new double[sizeLow];//vetor que guarda a probabilidade de Plower
    for (int i=0 ;i<sizeLow; i++)
    	probabilidadeLow[i] = (Plower[i]/NPixLow);///Npixels de determinado nivel de cinza / Npixels totais de Plower
    //}probabilidade de Plower
    	
    //probabilidade de Pupper{
    int NPixUpp=0;//Numero de pixels totais de Plower
    for (int i=0 ;i<sizeUpp; i++)
    	NPixUpp += Pupper[i];
    
    double probabilidadeUpp[] = new double[sizeUpp];//vetor que guarda a probabilidade de Pupper
    for (int i = 0 ;i<sizeUpp; i++)
    	probabilidadeUpp[i] = (Pupper[i]/NPixUpp);///Npixels de determinado nivel de cinza / Npixels de cinza de Pupper
    //}probabilidade de Pupper
   
    //}calculos de Probabilidade simples
    	
    	
    //calculos de Probabilidade acumulada{
    
    //Probabilidade acumulada de Plower{
    double CDFprobabilidadeLow[] = new double[sizeLow];
    CDFprobabilidadeLow[0]=probabilidadeLow[0];//caso inicial nivel de cinza 0
    for(int i=1;i<sizeLow;i++) 
    	CDFprobabilidadeLow[i]=probabilidadeLow[i]+CDFprobabilidadeLow[i-1];

    //}Probabilidade acumulada de Plower
    
    //Probabilidade acumulada de Pupper{
    double CDFprobabilidadeUpp[] = new double[sizeUpp];
    CDFprobabilidadeUpp[0]=probabilidadeUpp[0];//caso inicial nivel de cinza 0
    for(int i=1;i<sizeUpp;i++) 
    	CDFprobabilidadeUpp[i]=probabilidadeUpp[i]+CDFprobabilidadeUpp[i-1];

    //}Probabilidade acumulada de Pupper
    
    //}calculos de Probabilidade acumulada
   
    
    //calculos de NovoNivel{
    
    //novo nivel Plower
    double NovoNivelLow[] = new double[sizeLow];
    for(int i=0 ;i<sizeLow; i++)
    	NovoNivelLow[i]=CDFprobabilidadeLow[i] * (sizeLow-1);
    
    //novo nivel Pupper
    double NovoNivelUpp[] = new double[sizeUpp];
    for(int i=0 ;i<sizeUpp; i++)
    	NovoNivelUpp[i]=CDFprobabilidadeUpp[i] * (sizeUpp-1);
    
    //novo nivel juntando (novo nivel Plower) com (novo nivel Pupper)
    double NovoNivel[] = new double[256];
    for(int i=0 ;i<sizeLow; i++) {
    	NovoNivel[i]=NovoNivelLow[i];
    	System.out.printf("%d) %f\n",i,NovoNivel[i]);
}
    for(int j=0 ,i=sizeLow ;i<256; i++,j++) {
    	NovoNivel[i]=NovoNivelUpp[j];
    	System.out.printf("%d) %f\n",i,NovoNivel[i]);
    }
  //}calculos de NovoNivel
    
  //resultado final apos juncao de histogramas{
    BufferedImage res = new BufferedImage(img.getWidth(), img.getHeight(), img.getType());
    //copiar imagem original na imagem de resultado
    for(int i=0 ; i<img.getWidth() ; i++) {
		for(int j=0; j<img.getHeight(); j++) {
			
			Color x = new Color(img.getRGB(j, i));
			int corNova = (int) NovoNivel[x.getRed()]; 
            Color novo = new Color(corNova,corNova, corNova);
            res.setRGB(j, i, novo.getRGB());
        }
    }
    return res;
	//return res;
	//}resultado final apos juncao de histogramas
    
/*
     BufferedImage res = new BufferedImage(img.getWidth(), img.getHeight(), img.getType());//resultado final apos juncao de histogramas
    //copiar imagem original na imagem de resultado
    for(int i=0 ; i<img.getWidth() ; i++) {
		for(int j=0; j<img.getHeight(); j++) {
			int cor = (int) img.getRed();
			int corNova = (int) NovoNivelLow[cor]; 
            Color novo = new Color(corNova,corNova, corNova);
            res.setRGB(i, j, novo.getRGB());
        }
    }
	return res;*/
	
    //}calculos de NovoNivel{
    
    
    /*//Equalizacao HE de Plower e Pupper {
    
    //inicializa HEPlower
    double HEPlower[] = new double[sizeLow];
    for(int i=0 ;i<sizeLow; i++)
    	HEPlower[i]=0;
    //Recebe histograma Equalizado Plower
    for(int i=0 ;i<sizeLow; i++)
    	HEPlower[NovoNivelLow[i]]=Plower[i];
    
    //inicializa HEPUpper
    double HEPupper[] = new double[sizeUpp];
    for(int i=0 ;i<sizeUpp; i++)
    	HEPupper[i]=0;
    //Recebe histograma Equalizado Pupper
    for(int i=0 ;i<sizeUpp; i++)
    	HEPupper[NovoNivelUpp[i]]=Pupper[i];
    
    //}Equalizacao HE de Plower e Pupper 
    
    
    //concatenacao envolvendo HEPlower e HEPUpper para gerar o histograma final ISIHIOriginal[255] da (Illumination based Sub-Image Histogram Equalization,(ISIHI)){
    double ISIHIOriginal[] = new double[255];
    for(int i=0 ;i<sizeLow; i++)
    	ISIHIOriginal[i]=HEPlower[i];
    for(int i=0 , int j=sizeUpp ;i<sizeUpp; i++ , j++)
    	ISIHIOriginal[j]=HEPupper[i];
    //}concatenacao envolvendo HEPlower e HEPUpper para gerar o histograma final ISIHIOriginal[255] da (Illumination based Sub-Image Histogram Equalization,(ISIHI))
    
   
	
	
	

      */
}
    /**
     * Essa função é a implementação da método de GreennessG−R = (G + R)/(G - R)
     * onde o R,G e B são os valores obtido da imagem
     * @param img A imagem onde o filtro será aplicado
     * @return retorna a imagem depois de aplicado o filtro
     */
    public BufferedImage GreennGmenR(BufferedImage img) {
        BufferedImage res = new BufferedImage(img.getWidth(), img.getHeight(), img.getType());

        //COMEÇO NORMALIZAÇÃO
        double min = 10000;
        double max = 0;
        int zero;
        double cor = 0;
        
        try {
            
            for (int i = 0; i < img.getWidth(); i++) {
                for (int j = 0; j < img.getHeight(); j++) {
                    Color x = new Color(img.getRGB(i, j));

                    zero = x.getGreen() + x.getRed();

                    if(zero != 0){

                        cor = (x.getGreen() - x.getRed()) / ((x.getGreen() + x.getRed())); //Oq fazer se tiver um pixel preto?

                    }else{

                        cor = 0;

                    }

                    if (cor < min) {
                        min = cor;
                    }
                    if (cor > max) {
                        max = cor;
                    }
                }
            }

            
            for (int i = 0; i < img.getWidth(); i++) {
                for (int j = 0; j < img.getHeight(); j++) {
                    Color p = new Color(img.getRGB(i, j));

                    zero = p.getGreen() + p.getRed();
                    if(zero != 0){

                        double atual = (p.getGreen() - p.getRed()) / ((p.getGreen() + p.getRed()));
                        cor = 255 * ((atual - min) / (max - min));

                    }else{

                        cor = 0;

                    }

                    int corB31 = (int) cor;

                    Color novo = new Color(corB31, corB31, corB31);
                    res.setRGB(i, j, novo.getRGB());
                }
            }
        } catch (java.lang.ArithmeticException e) {
            
            JOptionPane.showMessageDialog(null, "Divisão por Zero", "Error", 0);
            
        }
        //FINAL NORMALIZAÇÃO

        
    return res;
    }
    
    /**
     * Essa função é a implementação da método de GreennessG−R = (G − R)/(G + R)
     * onde K é o valor passado pelo usuário e o R,G e B são os valores obtido da imagem
     * @param img A imagem onde o filtro será aplicado
     * @return retorna a imagem depois de aplicado o filtro
     */
    public BufferedImage GreennGmaisR(BufferedImage img) {
        BufferedImage res = new BufferedImage(img.getWidth(), img.getHeight(), img.getType());

        //COMEÇO NORMALIZAÇÃO
        double min = 10000;
        int zero;
        double max = 0;
        double cor = 0;

        try {
            

            for (int i = 0; i < img.getWidth(); i++) {
                for (int j = 0; j < img.getHeight(); j++) {
                    Color x = new Color(img.getRGB(i, j));

                    zero = x.getGreen() - x.getRed();

                    if(zero != 0){

                        cor = (x.getGreen() + x.getRed() / (x.getGreen() - x.getRed()));

                    }else{

                        cor = 0;

                    }

                        if (cor < min) {
                            min = cor;
                        }
                        if (cor > max) {
                            max = cor;
                        }
                    }
            }
       
        //FINAL NORMALIZAÇÃO

       
            for (int i = 0; i < img.getWidth(); i++) {
                for (int j = 0; j < img.getHeight(); j++) {
                    Color p = new Color(img.getRGB(i, j));

                    zero = p.getGreen() - p.getRed();

                    if(zero != 0){

                            double atual = (p.getGreen() + p.getRed() / (p.getGreen() - p.getRed()));
                            cor = 255 * ((atual - min) / (max - min));

                    }else{

                        cor = 0;

                    }

                        int corB31 = (int) cor;

                        Color novo = new Color(corB31, corB31, corB31);
                        res.setRGB(i, j, novo.getRGB());
                }
            }
        } catch (Exception e) {
            
            JOptionPane.showMessageDialog(null, "Divisão por Zero", "Error", 0);
            
        }
        
        
    return res;
    }

    /**
     * Essa função é a implementação da método de Greenness = (G)/(R + G + B)
     * onde o R,G e B são os valores obtido da imagem
     * @param img
     * @return 
     */
    public BufferedImage Greenn(BufferedImage img) {
        BufferedImage res = new BufferedImage(img.getWidth(), img.getHeight(), img.getType());

        //COMEÇO NORMALIZAÇÃO
        double min = 10000;
        double max = 0;

        for (int i = 0; i < img.getWidth(); i++) {
            for (int j = 0; j < img.getHeight(); j++) {
                Color x = new Color(img.getRGB(i, j));

                double cor = x.getGreen() - (x.getRed() + x.getGreen() + x.getBlue());

                if (cor < min) {
                    min = cor;
                }
                if (cor > max) {
                    max = cor;
                }
            }
        }
        //FINAL NORMALIZAÇÃO

        for (int i = 0; i < img.getWidth(); i++) {
            for (int j = 0; j < img.getHeight(); j++) {
                Color p = new Color(img.getRGB(i, j));

                double atual = p.getGreen() - (p.getRed() + p.getGreen() + p.getBlue() );

                double cor = 255 * ((atual - min) / (max - min));

                int corB32 = (int) cor;
                Color novo = new Color(corB32, corB32, corB32);
                res.setRGB(i, j, novo.getRGB());
            }
        }
        return res;
    }

    
    /**
     * Essa função é a implementação da método de GreennessSmolka = (G − Max{R,B})^2/G
     * onde o R,G e B são os valores obtido da imagem
     * @param img A imagem onde o filtro será aplicado
     * @return retorna a imagem depois de aplicado o filtro
     */
    public BufferedImage GreennSmolka(BufferedImage img) {
        BufferedImage res = new BufferedImage(img.getWidth(), img.getHeight(), img.getType());

        //COMEÇO NORMALIZAÇÃO
        double min = 10000;
        int zero;
        double max = 0;
        double cor = 0;
        double maior;

        try {
            

            for (int i = 0; i < img.getWidth(); i++) {
                for (int j = 0; j < img.getHeight(); j++) {
                    Color x = new Color(img.getRGB(i, j));

                    zero = x.getGreen();

                    if(zero != 0){

                        if(x.getRed() > x.getBlue()){
                            maior = x.getRed();
                        }else{
                            maior = x.getBlue();
                        }

                        cor = x.getGreen() - Math.pow((maior),9) / x.getGreen();

                    }else{

                        cor = 0;

                    }

                        if (cor < min) {
                            min = cor;
                        }
                        if (cor > max) {
                            max = cor;
                        }
                    }
            }
       
        //FINAL NORMALIZAÇÃO

       
            for (int i = 0; i < img.getWidth(); i++) {
                for (int j = 0; j < img.getHeight(); j++) {
                    Color p = new Color(img.getRGB(i, j));

                    zero = p.getGreen();

                    if(zero != 0){

                        if(p.getRed() > p.getBlue()){
                            maior = p.getRed();
                        }else{
                            maior = p.getBlue();
                        }

                        double atual = p.getGreen() - Math.pow(( maior),9) / p.getGreen();
                        cor = 255 * ((atual - min) / (max - min));
                        
                    }else{

                        cor = 0;

                    }

                            
                        int corB31 = (int) cor;

                        Color novo = new Color(corB31, corB31, corB31);
                        res.setRGB(i, j, novo.getRGB());
                }
            }
        } catch (Exception e) {
            
            JOptionPane.showMessageDialog(null, "Divisão por Zero", "Error", 0);
            
        }
        
    return res;
    }

    /**
    * Essa função é a implementação da método de GreennessG2 = (G^2 )/(B^2 + R^2 + k)
    * onde K é o valor passado pelo usuário e o R,G e B são os valores obtido da imagem
    * 
    * @param img A imagem onde o filtro será aplicado
    * @param K O valor K da equação
    * @return retorna a imagem depois de aplicado o filtro
    */
    public BufferedImage GreennG2(BufferedImage img) {
        BufferedImage res = new BufferedImage(img.getWidth(), img.getHeight(), img.getType());

        //COMEÇO NORMALIZAÇAO
        double min = 10000;
        double max = 0;
        double var = 14;
        double cor = 0;
        double maior;
        double zero;

        for (int i = 0; i < img.getWidth(); i++) {
            for (int j = 0; j < img.getHeight(); j++) {
                
                Color x = new Color(img.getRGB(i, j));
                
                zero = Math.pow(x.getBlue(),2) + Math.pow(x.getRed(),2) + var;

                if(zero != 0){

                    cor = Math.pow(x.getGreen(),2) / zero;

                }else{

                    cor = 0;

                }

                if (cor < min) {
                    min = cor;
                }
                if (cor > max) {
                    max = cor;
                }
            }
        }
        //FINAL NORMALIZAÇÃO

        for (int i = 0; i < img.getWidth(); i++) {
            for (int j = 0; j < img.getHeight(); j++) {

                Color p = new Color(img.getRGB(i, j));
                
                zero = Math.pow(p.getBlue(),2) + Math.pow(p.getRed(),2) + var;
                
                if(zero != 0){
                    
                    double atual = Math.pow(p.getGreen(),2) / zero;
                    cor = 255 * ((atual - min) / (max - min));

                }else{

                    cor = 0;

                }

                if (cor < min) {
                    min = cor;
                }
                if (cor > max) {
                    max = cor;
                }
                
                int corBK = (int) cor;

                Color novo = new Color(corBK, corBK, corBK);
                res.setRGB(i, j, novo.getRGB());
            }
        }
        return res;
    }
    
    /**
     * Essa função é a implementação da método de GreennessIPCA = I P CA = 0.7582|R − B| − 0.1168|R − G| + 0.6414|G − B|
     * onde o R,G e B são os valores obtido da imagem
     * @param img
     * @return 
     */
    public BufferedImage GreennIPCA(BufferedImage img) {
        BufferedImage res = new BufferedImage(img.getWidth(), img.getHeight(), img.getType());

        //COMEÇO NORMALIZAÇÃO
        double min = 10000;
        double max = 0;
        double RB, RG, GB;
        
        

        for (int i = 0; i < img.getWidth(); i++) {
            for (int j = 0; j < img.getHeight(); j++) {
                Color x = new Color(img.getRGB(i, j));
                
                RB = x.getRed() - x.getBlue();
                RG = x.getRed() - x.getGreen();
                GB = x.getGreen() - x.getBlue();

                //Colocar a função IPCA
                double cor = 0.7582*(Math.abs(RB)) - 0.1168*(Math.abs(RG)) + 0.6414*(Math.abs(GB));

                if (cor < min) {
                    min = cor;
                }
                if (cor > max) {
                    max = cor;
                }
            }
        }
        //FINAL NORMALIZAÇÃO

        for (int i = 0; i < img.getWidth(); i++) {
            for (int j = 0; j < img.getHeight(); j++) {
                Color p = new Color(img.getRGB(i, j));

                RB = p.getRed() - p.getBlue();
                RG = p.getRed() - p.getGreen();
                GB = p.getGreen() - p.getBlue();
                
                double atual = 0.7582*(Math.abs(RB)) - 0.1168*(Math.abs(RG)) + 0.6414*(Math.abs(GB));

                double cor = 255 * ((atual - min) / (max - min));

                int corB32 = (int) cor;
                Color novo = new Color(corB32, corB32, corB32);
                res.setRGB(i, j, novo.getRGB());
            }
        }
        return res;
    }
    
    public BufferedImage BIEspacoX(BufferedImage img) {
        BufferedImage res = new BufferedImage(img.getWidth(), img.getHeight(), img.getType());

        //COMEÇO NORMALIZAÇÃO
        double min = 100000;
        double max = 0;

        for (int i = 0; i < img.getWidth(); i++) {
            for (int j = 0; j < img.getHeight(); j++) {
               Color x = new Color(img.getRGB(i, j));
               
                //Conversão de RGB para Espaço de cor XYZ
                double Xx = x.getRed() * 0.4124 + x.getGreen() * 0.3575 + x.getBlue() * 0.1804;
                double Yx = x.getRed() * 0.2126 + x.getGreen() * 0.7156 + x.getBlue() * 0.0721;
                double Zx = x.getRed() * 0.0193 + x.getGreen() * 0.1191 + x.getBlue() * 0.9502;

                //Conversão de XYZ para L* a* b*
                double L = (116 * (Yx / 100) - 16);
                double a = 500 * ((Xx / 95.047) - (Yx / 100));
                double b = 200 * ((Yx / 100) - (Zx / 108.883));

                double P = (a + (1.75 * L)) / ((5.465 * L) + a - (3.012 * b));
                double cor = (100 * (P - 0.31)) / 0.17;

                if (cor < min) {
                    min = cor;
                }
                if (cor > max) {
                    max = cor;
                }
            }
        }
        //FINAL NORMALIZAÇÃO

        for (int i = 0; i < img.getWidth(); i++) {
            for (int j = 0; j < img.getHeight(); j++) {
                Color p = new Color(img.getRGB(i, j));
                //Conversão de RGB para Espaço de cor XYZ
                double X = p.getRed() * 0.4124 + p.getGreen() * 0.3575 + p.getBlue() * 0.1804;
                double Y = p.getRed() * 0.2126 + p.getGreen() * 0.7156 + p.getBlue() * 0.0721;
                double Z = p.getRed() * 0.0193 + p.getGreen() * 0.1191 + p.getBlue() * 0.9502;

                //Conversão de XYZ para L* a* b*
                double L = (116 * (Y / 100) - 16);
                double a = 500 * ((X / 95.047) - (Y / 100));
                double b = 200 * ((Y / 100) - (Z / 108.883));

                double P = (a + 1.75 * L) / (5.465 * L + a - 3.012 * b);
                double atual = (100 * (P - 0.31)) / 0.17;

                double cor = 255 * ((atual - min) / (max - min));

                int corBX = (int) cor;

                Color novo = new Color(corBX, corBX, corBX);
                res.setRGB(i, j, novo.getRGB());

            }
        }
        return res;
    }

    public BufferedImage BIEspacoI(BufferedImage img) {
        BufferedImage res = new BufferedImage(img.getWidth(), img.getHeight(), img.getType());

        //COMEÇO NORMALIZAÇÃO
        double min = 100000;
        double max = 0;

        for (int i = 0; i < img.getWidth(); i++) {
            for (int j = 0; j < img.getHeight(); j++) {
                Color x = new Color(img.getRGB(i, j));
                
                //Conversão de RGB para Espaço de cor XYZ
                double Xx = x.getRed() * 0.4124 + x.getGreen() * 0.3575 + x.getBlue() * 0.1804;
                double Yx = x.getRed() * 0.2126 + x.getGreen() * 0.7156 + x.getBlue() * 0.0721;
                double Zx = x.getRed() * 0.0193 + x.getGreen() * 0.1191 + x.getBlue() * 0.9502;

                //Conversão de XYZ para L* a* b*
                double L = (116 * (Yx / 1) - 16);

                double cor = 100 - L;

                if (cor < min) {
                    min = cor;
                }
                if (cor > max) {
                    max = cor;
                }
            }
        }
        //FINAL NORMALIZAÇÃO

        for (int i = 0; i < img.getWidth(); i++) {
            for (int j = 0; j < img.getHeight(); j++) {
                Color p = new Color(img.getRGB(i, j));

                //Conversão de RGB para Espaço de cor XYZ
                double X = p.getRed() * 0.4124 + p.getGreen() * 0.3575 + p.getBlue() * 0.1804;
                double Y = p.getRed() * 0.2126 + p.getGreen() * 0.7156 + p.getBlue() * 0.0721;
                double Z = p.getRed() * 0.0193 + p.getGreen() * 0.1191 + p.getBlue() * 0.9502;

                //Conversão de XYZ para L* a* b*
                double L = (116 * (Y / 1) - 16);
                double atual = 100 - L;
                double cor = 255 * ((atual - min) / (max - min));

                int corBII = (int) cor;
                
                Color novo = new Color(corBII, corBII, corBII);
                res.setRGB(i, j, novo.getRGB());

            }
        }
        return res;
    }
}
