// Semidefinite Programming (SDP) Relaxation for Max Cut
// Steps:
// 1. Formulate Integer Programming Problem
// 2. Relax Integer Constraints; relax integer constraints to allow for real-valued solutions
// 3. Formulate the SDP
// Express relaxed problem as a semidefinite program, involving a positive 
// semidefinite matrix variable 
// 4. Solve the SDP
// use a specialized SDP solver to find optimal solution to relaxed problem
// 5. Round the Solution
// round the solution of the SDP to obtain a feasible solution to the original integer program

// example using a library (CPLEX)

import ilog.concert.*;
import ilog.cplex.*;

public class MaxCutSDP
  {
    public static void main(String[] args)
    {
      try{
        // create new CPLEX model
        IloCplex cplex = new IloCplex();
        // input graph
        int[][] graph = {
          {0, 1, 1, 0},
          {1, 0, 1, 1},
          {1, 1, 0, 1},
          {0, 1, 1, 0}
        };
        int n = graph.length;
        // create decision variables- a symmetric matrix X
        IloNumVar[][] X = new IloNumVar[n][n];
        for(int i = 0; i < n; i++) {
          for(int j = 0; j <= i; j++) {
            X[i][j] = cplex.numVar(-1, 1);
            X[j][i] = X[i][j];
          }
        }
        // objective function; maximize sum of weights of cut edges
        IloLinearNumExpr objExpr = cplex.linearNumExpr();
        for(int i = 0; i < n; i++) {
          for(int j = i + 1; j < n; j++) {
            objExpr.addTerm(graph[i][j], X[i][j]);
          }
        }
        cplex.addMaximize(objExpr);
        // constraints; x is positive semidefinite
        IloLinearNumExpr[] quadraticForms = new IloLinearNumExpr[n];
        for(int i = 0; i < n; i++) {
          quadraticForms[i] = cplex.linearNumExpr();
          for(int j = 0; j < n; j++) {
            quadraticForms[i].addTerm(X[i][j], X[j][i]);
          }
        }
        cplex.addQuadratic(quadraticForms, cplex.numVar(0, Double.MAX_VALUE));
        // 2. diagonal elements of X are 1
        for(int i = 0; i < n; i++) {
          cplex.addEq(X[i][i], 1);
        }
        // solve SDP
        if(cplex.solve()) {
          double objValue = cplex.getObjValue();
          System.out.println("optimal objective value: " + objValue);
        }
        else{
          System.out.println("failed to solve the problem.");
        }
        cplex.end();
      }
      catch(Exception e) {
        e.printStackTrace();
      }
    }
  }
