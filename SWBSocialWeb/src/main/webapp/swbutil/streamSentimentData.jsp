<%-- 
    Document   : streamSentimentData
    Created on : 08-ago-2013, 11:51:58
    Author     : jorge.jimenez
--%>
<%@page contentType="text/json" pageEncoding="UTF-8"%> 
<%@page import="org.semanticwb.platform.SemanticObject"%>
<%@page import="org.semanticwb.social.*"%>
<%@page import="java.util.Iterator"%>
<%@page import="org.semanticwb.SWBUtils"%>
<%@page import="org.semanticwb.model.*"%>
<%@page import="org.semanticwb.SWBPortal"%> 
<%@page import="org.semanticwb.platform.SemanticProperty"%>
<%@page import="org.semanticwb.portal.api.*"%>
<%@page import="org.json.*"%>
<%@page import="java.util.*"%> 

<%!
    JSONArray getObject(Stream stream, String lang) throws Exception 
    {
        //WebSite wsite=WebSite.ClassMgr.getWebSite(stream.getSemanticObject().getModel().getName());
        System.out.println("Entra 3");
        int neutrals=0, positives=0, negatives=0;
        Iterator<PostIn> itStreamPostIns=stream.listPostInStreamInvs(); 
        while(itStreamPostIns.hasNext())
        {
            PostIn postIn=itStreamPostIns.next();
            if(postIn.getPostSentimentalType()==0){
                neutrals++; 
            }else if(postIn.getPostSentimentalType()==1){
                positives++;
            }else if(postIn.getPostSentimentalType()==2){ 
                negatives++;
            }
        }
        float intTotalVotos=positives+negatives+neutrals;
        
        //Positivo
        float intPorcentajePositive = ((float) positives * 100) / (float) intTotalVotos;
   
        System.out.println("Votos Positivos:"+positives+", porcentaje:"+intPorcentajePositive); 
        
        //Negativo
        float intPorcentajeNegative = ((float) negatives * 100) / (float) intTotalVotos;
        
        System.out.println("Votos negatives"+negatives+", porcentaje:"+intPorcentajeNegative); 
                
        //Neutro
        float intPorcentajeNeutral = ((float) neutrals * 100) / (float) intTotalVotos;
   
        System.out.println("Votos neutrals"+neutrals+", porcentaje:"+intPorcentajeNeutral);         
        
        
        System.out.println("Entra 4:"+positives+","+negatives+","+neutrals);
        JSONArray node=new JSONArray();
        
        if(intPorcentajePositive>0)
        {
            JSONObject node1=new JSONObject(); 
            node1.put("label", "positives"); 
            node1.put("value1", ""+positives);
            node1.put("value2", ""+intPorcentajePositive);
            node1.put("color", "#86c440");
            node.put(node1);
        }
        if(intPorcentajeNegative>0)
        {
            JSONObject node2=new JSONObject();
            node2.put("label", "negatives"); 
            node2.put("value1", ""+negatives);
            node2.put("value2", ""+intPorcentajeNegative);
            node2.put("color", "#990000");
            node.put(node2);
        }
        if(intPorcentajeNeutral>0)
        {
            JSONObject node3=new JSONObject();
            node3.put("label", "neutrals"); 
            node3.put("value1", ""+neutrals);
            node3.put("value2", ""+intPorcentajeNeutral);
            node3.put("color", "#eae8e3");
            node.put(node3);
        }
        return node;
    }
%>
<%
    System.out.println("Entra 0");
    if(request.getParameter("streamUri")!=null)
    {
        System.out.println("Entra 1:"+request.getParameter("streamUri"));
        SemanticObject semObj=SemanticObject.getSemanticObject(request.getParameter("streamUri"));
        Stream stream=(Stream) semObj.getGenericInstance();
        String lang=request.getParameter("lang");
        System.out.println("Entra 2:"+lang);
        out.println(getObject(stream, lang));
    }
%>