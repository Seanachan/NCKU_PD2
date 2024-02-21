//https://it5606.medium.com/threaded-binary-tree-1-3-57594c182606
#include<iostream>
#include<cstdlib>
#include<queue>
#include<iomanip>
using namespace std;
typedef struct Node Node;
struct Node{
    int val;
    Node *left,*right;
    bool leftBit,rightBit;
    // Node(int x): val(x), left(NULL), right(NULL){}
};
Node* root;
Node* threaded(Node* root){
    if(root==NULL)
        return NULL;
    if(root->left==NULL && root->right==NULL)
        return root;//it's a leaf node
    if(root->left){
        //there exist a left tree
        //find the predecessor of left tree
        Node* l=threaded(root->left);
        l->right=root;
        l->leftBit=true;//is a thread
    }
    if(root->right==NULL){
        //there exist a right tree
        return root;
    }
    return threaded(root->right);
}


void createNode(){
    int insertedVal[]={0,1,2,3,4,5,6,7,8,9,10,11,12,13,14};
    int value;
    Node* curNode;
    queue<Node*> q;
    int N=sizeof(insertedVal)/sizeof(insertedVal[0]);
    for(int i=0;i<N;i++){
        value=insertedVal[i];
        Node* newNode=new Node;
        newNode->val=insertedVal[i];

        cout<<setw(2)<<value<<" ";
        if(!root){
            newNode->val=value;
            root=newNode;
            curNode=root;
            continue;
        }

        else{
            int flag=0;
            while(!flag){
                if(curNode->left==NULL){
                    curNode->left=newNode;
                    q.push(curNode->left);
                    flag=1;
                    continue;
                }
                else if(curNode->right==NULL){
                    curNode->right=newNode;
                    q.push(curNode->right);
                    flag=1;
                    continue;
                }
                else{
                    curNode=q.front();
                    q.pop();
                }
            }
        }
    }   
}
void recTraversal(Node* node){
    //inOrder
    recTraversal(node->left);
    cout<<setw(2)<<node->val<<" ";
    recTraversal(node->right);
}
void levelTraversal(){
    //levelOrder traversal
    queue<Node*> q;
    q.push(root);
    while(!q.empty()){
        Node* curNode = q.front();
        q.pop();
        cout<<setw(2)<<curNode->val<<" ";

        if(curNode->left) 
            q.push(curNode->left);
        if(curNode->right)
            q.push(curNode->right);
    }
    return;
}
void printAllNodes(Node* curNode){
    if(!curNode) return;
    
    printAllNodes(curNode->left);
    cout<<setw(2)<<curNode->val<<" ";
    printAllNodes(curNode->right);
    
}
int main(){
    createNode();//create binary tree
    cout<<endl;
    printAllNodes(root);
    // threaded(root );
    cout<<endl;
    levelTraversal();
    return 0;
}