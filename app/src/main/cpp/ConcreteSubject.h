//
// Created by zhangyapeng on 18-11-2.
//

#ifndef C_PROJECT_CONCRETESUBJECT_H
#define C_PROJECT_CONCRETESUBJECT_H
#include "subject.h"
#include "IObserver.h"
#include <list>
using namespace std;
class ConcreteSubject : public subject {
private:
    list<IObserver *> m_observers;
    float m_fPrice;
public:
    virtual void Attach(IObserver *) ;
    virtual void Detach(IObserver *) ;
    virtual void Notify() ;
    ConcreteSubject() { m_fPrice = 10.0; }
    void SetPrice(float price);
    virtual ~ConcreteSubject(){
        LOGE("ConcreteSubject delete");
        list<IObserver *>::iterator it = m_observers.begin();
        while (it != m_observers.end()) {
//            (*it)->Update(m_fPrice);
            delete(*it);
            ++it;

        }
    };
};



#endif //C_PROJECT_CONCRETESUBJECT_H
