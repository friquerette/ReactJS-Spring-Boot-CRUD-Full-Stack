import React, { Component } from 'react'
import EmployeeService from '../services/EmployeeService'

class ListEmployeeComponent extends Component {
    constructor(props) {
        super(props)

        this.state = {
            employees: [],
            oneQuestion: '',
            theAnswerFromAI: 'aaa'
        }
        this.addEmployee = this.addEmployee.bind(this);
        this.editEmployee = this.editEmployee.bind(this);
        this.deleteEmployee = this.deleteEmployee.bind(this);
    }

    deleteEmployee(id){
        EmployeeService.deleteEmployee(id).then( res => {
            this.setState({employees: this.state.employees.filter(employee => employee.id !== id)});
        });
    }
    viewEmployee(id){
        this.props.history.push(`/view-employee/${id}`);
    }
    editEmployee(id){
        this.props.history.push(`/add-employee/${id}`);
    }

    componentDidMount(){
        EmployeeService.getEmployees().then((res) => {
            this.setState({ employees: res.data});
        });
    }

    addEmployee(){
        this.props.history.push('/add-employee/_add');
    }

    refreshOneQuestion= () => {
        if (!this.state.oneQuestion) {
            alert('you ask nothing');
            return
        }
        this.state.theAnswerFromAI = '';
        EmployeeService.askOneQuestion(this.state.oneQuestion).then(res => {
            console.log(res.data)
            this.setState({theAnswerFromAI: res.data});
        });
    }

    changeOneQuestionHandler= (event) => {
        this.setState({oneQuestion: event.target.value});
    }

    changeTheAnswerFromAIHandler= (event) => {
        this.setState({oneQuestion: event.target.value});
    }

    render() {
        return (
            <div>
                 <h2 className="text-center">Employees List</h2>
                 <div className = "row">
                    <button className="btn btn-primary" onClick={this.addEmployee}> Add Employee</button>
                 </div>
                 <br></br>
                 <div className = "row">
                        <table className = "table table-striped table-bordered">

                            <thead>
                                <tr>
                                    <th> Employee First Name</th>
                                    <th> Employee Last Name</th>
                                    <th> Employee Email Id</th>
                                    <th> Actions</th>
                                </tr>
                            </thead>
                            <tbody>
                                {
                                    this.state.employees.map(
                                        employee => 
                                        <tr key = {employee.id}>
                                             <td> { employee.firstName} </td>   
                                             <td> {employee.lastName}</td>
                                             <td> {employee.emailId}</td>
                                             <td>
                                                 <button onClick={ () => this.editEmployee(employee.id)} className="btn btn-info">Update </button>
                                                 <button style={{marginLeft: "10px"}} onClick={ () => this.deleteEmployee(employee.id)} className="btn btn-danger">Delete </button>
                                                 <button style={{marginLeft: "10px"}} onClick={ () => this.viewEmployee(employee.id)} className="btn btn-info">View </button>
                                             </td>
                                        </tr>
                                    )
                                }
                            </tbody>
                        </table>
                 </div>
                <div className = "row">
                    Your question:
                    <input placeholder="the wanted question" name="question" className="form-control"
                           value={this.state.oneQuestion} onChange={this.changeOneQuestionHandler}/>
                </div>
                <div className = "row">
                    <button className="btn btn-success" onClick={this.refreshOneQuestion}>Ask</button>
                </div>
                <div className = "row">
                    The answer from the AI:
                    <textarea value={this.state.theAnswerFromAI} className="form-control"  onChange={this.changeTheAnswerFromAIHandler}></textarea>
                </div>
            </div>
        )
    }
}

export default ListEmployeeComponent
