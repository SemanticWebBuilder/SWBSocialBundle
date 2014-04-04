/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.semanticwb.social.listener;

import java.util.Calendar;
import java.util.Iterator;
import java.util.Timer;
import java.util.TimerTask;
import org.semanticwb.Logger;
import org.semanticwb.SWBUtils;
import org.semanticwb.model.SWBContext;
import org.semanticwb.social.PostMonitor;
import org.semanticwb.social.PostOutNet;
import org.semanticwb.social.SocialMonitorable;
import org.semanticwb.social.util.SWBSocialUtil;

/**
 *
 * @author jorge.jimenez
 * Clase que monitorea redes sociales de tipo MonitorAble (Youtube), que se pueden tardar tiempo para publicar un mensaje.
 */
public class MonitorMgr {
    
    private static Logger log = SWBUtils.getLogger(MonitorMgr.class);
    static final int MILISEG_IN_SEGUNDO=1000;
    
    
    
    /*
     * Metodo constructor que levanta listener de cada uno de los streams de cada sitio de tipo SWBSocial
     */
    public MonitorMgr() {
        try {
            int periodTime = (60*MILISEG_IN_SEGUNDO)*3; //3 minutos
            Timer timer = new Timer();
            int initTime=180*MILISEG_IN_SEGUNDO;
            log.event("Initializing MonitorMgr, starts starts in:"+initTime+"ms,periodicity:"+periodTime+"ms");
            timer.schedule(new MonitorTask(), initTime,periodTime);
        } catch (Exception e) {
            log.error(e);
        }
    }
    
    
     /**
     * Clase de tipo Timer, hecha a andar las redes sociales adjudicadas a un stream.
     */ 
    private static class MonitorTask extends TimerTask
    {
        public MonitorTask()
        {
        }

        public void run() {
            //System.out.println("Entra a MonitorMgr/Run-1");
            if(PostMonitor.ClassMgr.listPostMonitors(SWBContext.getAdminWebSite()).hasNext())
            {
                PostMonitor postMonitor=PostMonitor.ClassMgr.listPostMonitors(SWBContext.getAdminWebSite()).next();
                //System.out.println("Entra a MonitorMgr/Run-2/postMonitor:"+postMonitor);
                Iterator <PostOutNet> itPostOutNets=postMonitor.listPostOutNets();
                while(itPostOutNets.hasNext())
                {
                    PostOutNet postOutNet=itPostOutNets.next();
                    //System.out.println("Entra a MonitorMgr/Run-2/postMonitor:"+postOutNet);
                    if(postOutNet!=null)
                    {
                        //System.out.println("Entra a MonitorMgr/Run-3/postOutNet:"+postOutNet.getSocialNetwork());
                        if(postOutNet.getSocialNetwork() instanceof SocialMonitorable)
                        {
                            SocialMonitorable socialMonitorAbleClass=(SocialMonitorable)postOutNet.getSocialNetwork();
                            //System.out.println("Entra a MonitorMgr.. Para enviar a monitorear cuenta de red:"+postOutNet.getSocialNetwork());
                            //System.out.println("Entra a MonitorMgr/Run-4/socialMonitorAbleClass:"+socialMonitorAbleClass);

                            //Va ha entrar al siguinte if si la red social me regresa en el metodo isPublished un true,
                            //eso significa que voy a quitar a esa instancia de PostOutNet de mi unico PostMonitor (el de admin) y 
                            //eso implicaría que ya no lo tomaría en cuenta el timer para la sig.vez que se ejecute, de lo contrario,
                            //el timer lo tomara nuevamente en cuenta la sig. vez.
                            if(socialMonitorAbleClass.isPublished(postOutNet)) 
                            {
                                postMonitor.removePostOutNet(postOutNet);  //Elimino el PostOutNet de mi PostMonitor, para que ya no se tome en cuenta en el timer la sig. vez
                                //System.out.println("Entra a MonitorMgr/Run-5/ISpublished Y SE QUITA DE TIMER");
                                /*
                                if(postOutNet.getStatus()==0) //Quiere decir que aunque se va ha quitar de ejecutar en el timer el postOutNet, pero que en realidad el PostOut no se publicó por causa de un error en la Red Social
                                {
                                    //System.out.println("Entra a MonitorMgr/Run-6/ESTATUS 0");
                                }else{  //Si se publicó en la red social de manera satisfactoria (Sin error)
                                    //TODO:Talvez ver si todos los PostOutNet en donde esta un PostOut estan con estus 1(publicado), entonces, poner el PostOut como publicado
                                    //Talvez esto lo debo hacer cuando listo los PostOuts, revisar ahi y si es asi, cambio el estatus desde ahi, para no volver a revisar ese 
                                    //PostOut la siguiente vez en sus PostOutNet, lo cual puede tardar mucho.
                                    //System.out.println("Entra a MonitorMgr/Run-5/ESTATUS 1");
                                }**/
                            }else if(postOutNet.getPo_created()!=null){
                                int days=SWBSocialUtil.Util.Datediff(postOutNet.getPo_created(), Calendar.getInstance().getTime());
                                System.out.println("Monitor/respuesta:False--->Days:"+days);
                                if(days>=1)
                                {
                                    System.out.println("Va a eliminar de Monitor el PostOutNet:"+postOutNet);
                                    postMonitor.removePostOutNet(postOutNet);
                                    System.out.println("Elimino de Monitor el PostOutNet:"+postOutNet);
                                }
                            }else{
                                postMonitor.removePostOutNet(postOutNet);
                            }
                        }
                    }
                }
            }
        }
     }
    
    
    
}