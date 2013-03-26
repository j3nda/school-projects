#include "zasobnik.h"


zasobnik::zasobnik()
{
    this->head = NULL;
}


zasobnik::~zasobnik()
{
    while (!isEmpty()) {
        pop();
    }
}


vagon *zasobnik::push(vagon *item)
{
    t_item *p = new t_item;

    p->ptr = item;
    if (isEmpty()) {
        p->next = NULL;

    } else {
        p->next = head;
    }
    head = p;
    return item;
}


vagon *zasobnik::pop()
{
    if (!isEmpty()) {
        t_item *p = head;

        head = head->next;

        vagon *x = p->ptr;

        delete p;
        return x;

    } else {
        fprintf(stderr, "underflow!\n");
        return NULL;
    }
}


bool zasobnik::isEmpty()
{
    return head == NULL;
}

