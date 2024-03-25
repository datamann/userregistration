import { Button } from "@hilla/react-components/Button.js";
import { TextField } from "@hilla/react-components/TextField.js";
import { EmailField } from "@hilla/react-components/EmailField.js";
import { Grid } from "@hilla/react-components/Grid.js";
import { GridColumn, GridColumnElement } from "@hilla/react-components/GridColumn";
import { Tooltip } from "@hilla/react-components/Tooltip.js";
import { useEffect, useState } from "react";
import { useForm } from '@hilla/react-form';
import User from "../generated/no/sivertsensoftware/userregistration/model/User";
import { UserController } from "../generated/endpoints";
import UserModel from "Frontend/generated/no/sivertsensoftware/userregistration/model/UserModel";

export default function MainView() {

  const [items, setItems] = useState<User[]>();
  const [isadmin, setIsAdmin] = useState<boolean>();

  useEffect(() => {
    const getAllUsers = async () => {
      const result: any = await UserController.findAll();
      setItems(result);
    };
    getAllUsers();
    isAdmin();
  }, [setItems]);

  async function isAdmin() {
    setIsAdmin(await UserController.mayBeAllowed().catch(() => false));
  }

  async function createUser(user: User) {
    await UserController.createUser(user);
  }

  function userToCreate(user: User) {
    const userCreated = createUser(user); 

    function handleReplyCreateUser(){
      setItems([...items!,user]);
    }

    function userNotCreated() {
      alert('User NOT created!');
    }
    userCreated.then(handleReplyCreateUser,userNotCreated);
  };

  const { model, field, read, submit } = useForm(UserModel, {
    onSubmit: async (user: User) => {
      userToCreate(user);
    }
  });

  async function deleteUser(id: number) {
    await UserController.deleteById(id);
  }

  function deleteSelectedUser(id: any, user: UserModel) {

    const userToDelete = deleteUser(id);

    function handleReplyDeleted() {
      const update = items!.filter((user) => id !== user.id);
      setItems(update);
    }

    function handleReply(){
      alert('User NOT deleted!');
    }

    userToDelete.then(handleReplyDeleted, handleReply);

  };

  return (  
    <>
      <form className="flex flex-col gap-y-2">
      <TextField label="First name" {...field(model.first_name)}
        name="first_name"
        placeholder="First name"
        autocomplete="given-name"
        disabled={!isadmin}
        >
        <Tooltip slot="tooltip" text="Enter first name!" />
      </TextField>
      
      <TextField label="Last name" {...field(model.last_name)}
        name="last_name"
        placeholder="Last name"
        autocomplete="family-name"
        disabled={!isadmin}
        >
        <Tooltip slot="tooltip" text="Enter last name!" />
        </TextField>

      <EmailField className="gap-s" {...field(model.email)}
        label="E-Mail"
        name="email"
        placeholder="E-Mail"
        autocomplete="on"
        disabled={!isadmin}
        >
        <Tooltip slot="tooltip" text="Enter a valid email address!" />
      </EmailField>

      <Button disabled={!isadmin} theme="primary" onClick={submit}>
      <Tooltip slot="tooltip" text="Click, too add record!" />
        Submit
      </Button>

      <Grid className="flex-grow" items={items} allRowsVisible>
      <GridColumn header="ID" path="id" autoWidth></GridColumn>
        <GridColumn header="First name" path="first_name" autoWidth></GridColumn>
        <GridColumn header="Last name" path="last_name" autoWidth></GridColumn>
        <GridColumn header="E-Mail" path="email" autoWidth></GridColumn>
        <GridColumn header="Actions" path="actions" autoWidth className="background" renderer={({ item: User }) => (<Button disabled={!isadmin} theme="primary" onClick={(item) => { deleteSelectedUser(User.id, User); }}>Delete</Button>)}></GridColumn>
      </Grid>
    </form>
    </>
  );
}

