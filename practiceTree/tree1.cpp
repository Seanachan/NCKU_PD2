#include<iostream>
using namespace std;
#define N 16
class TreeNode{
    public:
    int val;
    struct TreeNode* left;
    struct TreeNode* right;
};
typedef class TreeNode Node;
Node* rootNode=(Node*) new Node;
int log(int n){
    int ans=0;
    while(n>0){
        n/=2;
        ans++;
    }
    return ans;
}

void addNode(int value){
    Node* curNode,*newNode;
    newNode=(Node*) new Node;//malloc(sizeof(Node))
    if(!rootNode){
        newNode=curNode;
        newNode->val=value;
    }else{
        curNode=rootNode;
        int flag=0;
        while(!flag){
            if(value<curNode->val){
                if(curNode->left==NULL){
                    curNode->left=newNode;
                    flag=1;
                }
                else
                    curNode=curNode->left;
            }else{
                if(curNode->right==NULL){
                    curNode->right=newNode;
                    flag=1;
                }else
                    curNode=curNode->right;
            }
        }
    }
}
int main(){
    int insertVal[N];
    for(int i=1;i<=N;i++)
        insertVal[i-1]=i;
    for(int i=0;i<N;i++){
        addNode(insertVal[i]);
    }


    return 0;
}