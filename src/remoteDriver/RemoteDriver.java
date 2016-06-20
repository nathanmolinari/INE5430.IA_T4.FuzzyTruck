package remoteDriver;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.StringTokenizer;
 
public class RemoteDriver {
	
	static int port = 4321;
	static String host = "localhost";
	
	static  double x, y, angle;
	
    public static void main(String[] args) throws IOException {
        	    	
        Socket kkSocket = null;
        PrintWriter out = null;
        BufferedReader in = null;
 
        try {
            kkSocket = new Socket(host, port);
            out = new PrintWriter(kkSocket.getOutputStream(), true);
            in = new BufferedReader(new InputStreamReader(kkSocket.getInputStream()));
        } catch (UnknownHostException e) {
            System.err.println("Don't know about host:"  + host);
            System.exit(1);
        } catch (IOException e) {
            System.err.println("Couldn't get I/O for the connection to: " + host);
            System.exit(1);
        }
 
        BufferedReader stdIn = new BufferedReader(new InputStreamReader(System.in));
        String fromServer;
 
       
        
        // requisicao da posicao do caminhao
        out.println("r");
        while ((fromServer = in.readLine()) != null) {
        	StringTokenizer st = new StringTokenizer(fromServer);
        	x = Double.valueOf(st.nextToken()).doubleValue();
        	y = Double.valueOf(st.nextToken()).doubleValue();
        	angle = Double.valueOf(st.nextToken()).doubleValue();

        	System.out.println("x: " + x + " y: " + y + " angle: " + angle);
        	
        	/////////////////////////////////////////////////////////////////////////////////////
        	// sua logica fuzzy vai aqui use os valores de x,y e angle obtidos. x e y estao em [0,1] e angulo [0,360)
        	
        	
 
        	double foco;
        	if(x >= 0.48 && x <= 0.52){
        		foco = 90;
        	}
        	
        	if(x >= 0.4 && x <= 0.6){
        		foco = (x > 0.52) ? 75 : 105;
        	}
        	else{
        		foco = (x > 0.6) ? 25 : 155;
        	}
        	double respostaDaSuaLogica = angular(foco);
			
        	
        	System.out.println(respostaDaSuaLogica);

				
  
        	
        	
         // atribuir um valor entre -1 e 1 para virar o volante pra esquerda ou direita.
        	
        	
        	///////////////////////////////////////////////////////////////////////////////// Acaba sua modificacao aqui
        	// envio da acao do volante
        	out.println(respostaDaSuaLogica);
        	
            // requisicao da posicao do caminhao        	
        	out.println("r");	
        }
 
        out.close();
        in.close();
        stdIn.close();
        kkSocket.close();
    }
    
       
    public static double angular(double _angle){
    	
    	double respostaDaSuaLogica = 0;
    	double direcao;
    	if(_angle < 180){
    		direcao = (angle > (180 + _angle) || angle < _angle) ? 1 : -1; 
    	}
    	else{
    		direcao = (angle > (180 + _angle) % 360 && angle < _angle) ? 1 : -1;
    	}
    	
    	if(angle < _angle || angle >= (180 + _angle) % 360){
			if(angle <= _angle - 30 || angle >= 180 + _angle){
				respostaDaSuaLogica = direcao;
			}
			else{
				respostaDaSuaLogica = direcao * ((_angle - angle) / 360);
			}
		}
		
		
		if(angle > _angle && angle < (180 + _angle) % 360){
			if(angle > _angle + 30){
				respostaDaSuaLogica = direcao;
			}
			else{
				respostaDaSuaLogica = direcao * ((angle - _angle) / 360);
			}
		}
		
		if(angle == _angle){
			respostaDaSuaLogica = 0;
		}
		
		return respostaDaSuaLogica;
		
    }

}