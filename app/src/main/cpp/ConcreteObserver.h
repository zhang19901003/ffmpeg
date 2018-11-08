//
// Created by zhangyapen
// g on 18-11-2.
//

#ifndef C_PROJECT_CONCRETEOBSERVER_H
#define C_PROJECT_CONCRETEOBSERVER_H

#include <string>

using namespace std;
#include "IObserver.h"

class ConcreteObserver : public IObserver {
public:
    ConcreteObserver(string name) {
        LOGE("ConcreteObserver custore");
        m_strName = name;
    }

    void Update(float price) ;

    ~ ConcreteObserver(){
        LOGE("ConcreteObserver delete %s",m_strName.c_str());
    }

private:
    string m_strName;

};


#endif //C_PROJECT_CONCRETEOBSERVER_H
