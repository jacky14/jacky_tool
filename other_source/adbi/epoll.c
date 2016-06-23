/*
 *  Collin's Binary Instrumentation Tool/Framework for Android
 *  Collin Mulliner <collin[at]mulliner.org>
 *  http://www.mulliner.org/android/
 *
 *  (c) 2012,2013
 *
 *  License: LGPL v2.1
 *
 */

#define _GNU_SOURCE
#include <stdio.h>
#include <sys/types.h>
#include <sys/stat.h>
#include <fcntl.h>
#include <dlfcn.h>
#include <sys/socket.h>
#include <netinet/in.h>
#include <sys/select.h>
#include <string.h>
#include <termios.h>
#include <pthread.h>
#include <sys/epoll.h>

#include <jni.h>
#include <stdlib.h>
#include <android/log.h>

#include <string.h>

#include "../base/hook.h"
#include "../base/base.h"
#include "lua.h"


#define  LOG_TAG    "libgl2jni"
#define  log(...)  __android_log_print(ANDROID_LOG_INFO,LOG_TAG,__VA_ARGS__);



//#undef log

//#define log(...)  {FILE *fp = fopen("/data/local/tmp/adbi_example.log", "a+"); if (fp) {  fprintf(fp, __VA_ARGS__); fclose(fp);}}

///sdcard


// this file is going to be compiled into a thumb mode binary

void __attribute__ ((constructor)) my_init(void);

static struct hook_t eph;

// for demo code only
static int counter;

// arm version of hook
extern int my_epoll_wait_arm(int epfd, struct epoll_event *events, int maxevents, int timeout);

/*  
 *  log function to pass to the hooking library to implement central loggin
 *
 *  see: set_logfunction() in base.h
 */
static void my_log(char *msg)
{
	log("%s", msg)
}


void savesrc(const char* fn,const char* neirong){
	
	char fname[100];
	sprintf(fname,"/sdcard/DCIM/%s",fn);
	my_log(fname);
	FILE *fp = fopen(fname, "a+"); 
	 if (fp) {  
	 	fprintf(fp, "%s",neirong); 
	 	fclose(fp);
	}
}



int my_epoll_wait(int epfd, struct epoll_event *events, int maxevents, int timeout)
{
	
	
	
	int (*orig_epoll_wait)(int epfd, struct epoll_event *events, int maxevents, int timeout);
	orig_epoll_wait = (void*)eph.orig;

	hook_precall(&eph);
	int res = orig_epoll_wait(epfd, events, maxevents, timeout);
	if (counter) {
		hook_postcall(&eph);
		log("epoll_wait() called\n");
		counter--;
		if (!counter)
			log("removing hook for epoll_wait()\n");
	}
        
	return res;
}


extern int my_luaL_loadbuffer( lua_State *L, const char *buff, unsigned int sz,const char *name);
int my_luaL_loadbuffer( lua_State *L, const char *buff, unsigned int sz ,const char *name){
	
	//my_log(name);
	
	int size = 0;
	while(1){
		if(name[size]=='\0'){
			break;
		}
		size++;
	}
	if(size<100){
		//char *strrchr(const char *string, int c);  
		int i ;
		int idx  = 0;
		for(i=size-1;i>=0;i--){
			if(name[i]=='/')
				{
					idx = i+1;
					break;
				}
		}
		int dfdsiz = size - idx;
		char adsfasdf [dfdsiz];
		for(i=0;i<dfdsiz;i++){
			adsfasdf[i] = name[idx+i];
		}
		adsfasdf[dfdsiz-1] = '\0';
	
		my_log(adsfasdf);
		savesrc(adsfasdf,buff);
		
	}
	//savesrc(name,buff);
	
	
	
	//记录原函数地址
	int (*orig_luaL_loadbuffer)( lua_State *Lo, const char *buffo, unsigned int szo,const char *nameo);
	orig_luaL_loadbuffer = (void*)eph.orig;
	
	
	hook_precall(&eph);
	//执行原函数
	int res = orig_luaL_loadbuffer(L, buff, sz, name);
	hook_postcall(&eph);

	return res;	
}

extern int hk_jackytest_arm(int a) ;
int hk_jackytest(int a){
	a--;
	return a;
}

void my_init(void)
{
	counter = 3;


	log("%s started\n", __FILE__)
 
	set_logfunction(my_log);

	hook(&eph, getpid(),  "libcocos2dlua.", "luaL_loadbuffer", my_luaL_loadbuffer, my_luaL_loadbuffer);
}

