//
// Created by zhangyapeng on 18-10-17.
//

#ifndef C_PROJECT_XTHREAD_H
#define C_PROJECT_XTHREAD_H


class XThread {
public:
    virtual void Start();
    virtual void Stop();
    virtual void Main(){

    }

private:
    void ThreadMain();
};


#endif //C_PROJECT_XTHREAD_H
