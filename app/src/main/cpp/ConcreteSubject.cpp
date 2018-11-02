//
// Created by zhangyapeng on 18-11-2.
//

#include "ConcreteSubject.h"

void ConcreteSubject::SetPrice(float price) {
    m_fPrice = price;
}

void ConcreteSubject:: Attach(IObserver *observer) {
    m_observers.push_back(observer);
}

void ConcreteSubject:: Detach(IObserver *observer) {
    m_observers.remove(observer);
}

void ConcreteSubject::Notify() {
    list<IObserver *>::iterator it = m_observers.begin();
    while (it != m_observers.end()) {
        (*it)->Update(m_fPrice);
        ++it;
    }
}
